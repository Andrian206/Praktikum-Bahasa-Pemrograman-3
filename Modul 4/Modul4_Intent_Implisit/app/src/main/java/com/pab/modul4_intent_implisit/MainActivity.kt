package com.pab.modul4_intent_implisit

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.net.Uri
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_buka_browser = findViewById<Button>(R.id.btn_buka_browser)
        val edt_url = findViewById<EditText>(R.id.edt_url)
        val btn_kirim_email = findViewById<Button>(R.id.button_send_email)
        val edt_email = findViewById<EditText>(R.id.edt_email)
        val edt_subject = findViewById<EditText>(R.id.edt_subject)
        val edt_isi_email = findViewById<EditText>(R.id.edt_isi_email)
        val btn_buka_telepon = findViewById<Button>(R.id.btn_buka_telepon)
        val edt_telepon = findViewById<EditText>(R.id.edt_telepon)

        btn_buka_browser.setOnClickListener {
            val url = edt_url.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + url))
            startActivity(intent)
        }

        btn_kirim_email.setOnClickListener {
            val edt_email = edt_email.text.toString()
            val edt_subject = edt_subject.text.toString()
            val edt_isi_email = edt_isi_email.text.toString()
            if (edt_email.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(edt_email))
                intent.putExtra(Intent.EXTRA_SUBJECT, edt_subject)
                intent.putExtra(Intent.EXTRA_TEXT, edt_isi_email)
                startActivity(intent)
            }
        }

        btn_buka_telepon.setOnClickListener {
            val nomor_telepon = edt_telepon.text.toString()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$nomor_telepon"))
            startActivity(intent)
        }
    }
}