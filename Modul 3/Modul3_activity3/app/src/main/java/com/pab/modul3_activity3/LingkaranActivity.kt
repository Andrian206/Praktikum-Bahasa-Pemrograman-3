package com.pab.modul3_activity3

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LingkaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lingkaran)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_hitung = findViewById<Button>(R.id.btn_hitung)
        var jari = findViewById<EditText>(R.id.edt_jari)
        var hasil= findViewById<TextView>(R.id.hasil)

        btn_hitung.setOnClickListener {
            val jariText = jari.text.toString()

            if (jariText.isNotEmpty()) {
                val jariValue = jariText.toDouble()
                val luas = 3.14 * jariValue * jariValue
                hasil.text = "$luas"
            } else {
                hasil.text = "Jari-Jari harus diisi!"
            }
        }

        val btn_pindah = findViewById<Button>(R.id.btn_pindah_halaman_utama)
        btn_pindah.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}