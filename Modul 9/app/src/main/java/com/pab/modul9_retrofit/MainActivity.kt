package com.pab.modul9_retrofit
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi RecyclerView dan komponen lainnya
        val headerImage = findViewById<ImageView>(R.id.thumbnail)
        val headerTitle = findViewById<TextView>(R.id.title)
        recyclerView = findViewById(R.id.main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Ambil data dari API
        NewsApiClient.instance.getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsData = response.body()?.data
                    val news = newsData?.posts ?: emptyList()

                    // Set Data Header
                    headerTitle.text = newsData?.title
                    Glide.with(this@MainActivity).load(newsData?.image).into(headerImage)

                    // Set adapter untuk RecyclerView
                    newsAdapter = NewsAdapter(news)
                    recyclerView.adapter = newsAdapter
                } else {
                    // Tangani kesalahan jika respons tidak berhasil
                    showErrorDialog("Sepertinya ada masalah.....")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Tangani kesalahan jika permintaan gagal
                showErrorDialog("An error occurred: ${t.message}")
            }
        })
    }
}