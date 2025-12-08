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

class PersegiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_persegi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_hitung = findViewById<Button>(R.id.btn_hitung)
        var sisi = findViewById<EditText>(R.id.edt_sisi)
        var hasil= findViewById<TextView>(R.id.hasil)

        btn_hitung.setOnClickListener {
            val sisiText = sisi.text.toString()

            if (sisiText.isNotEmpty()) {
                val sisiValue = sisiText.toDouble()
                val luas = sisiValue * sisiValue
                hasil.text = luas.toString()
            } else {
                hasil.text = "Sisi harus diisi!"
            }
        }

        val btn_pindah = findViewById<Button>(R.id.btn_pindah_halaman_utama)
        btn_pindah.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}