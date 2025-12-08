package com.pab.modul3_activity3

import android.os.Bundle
import android.widget.Button
import android.content.Intent
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

        val btnPersegi = findViewById<Button>(R.id.btn_persegi)
        val btnSegitiga = findViewById<Button>(R.id.btn_segitiga)
        val btnLingkaran = findViewById<Button>(R.id.btn_lingkaran)
        val btnPersegiPanjang = findViewById<Button>(R.id.btn_persegipanjang)

        btnPersegi.setOnClickListener {
            //intent untuk berpindah activity
            val intent = Intent(this, PersegiActivity::class.java)
            startActivity(intent)
        }

        btnSegitiga.setOnClickListener {
            //intent untuk berpindah activity
            val intent = Intent(this, SegitigaActivity::class.java)
            startActivity(intent)
        }

        btnLingkaran.setOnClickListener {
            //intent untuk berpindah activity
            val intent = Intent(this, LingkaranActivity::class.java)
            startActivity(intent)
        }

        btnPersegiPanjang.setOnClickListener {
            //intent untuk berpindah activity
            val intent = Intent(this, PersegiPanjangActivity::class.java)
            startActivity(intent)
        }
    }
}