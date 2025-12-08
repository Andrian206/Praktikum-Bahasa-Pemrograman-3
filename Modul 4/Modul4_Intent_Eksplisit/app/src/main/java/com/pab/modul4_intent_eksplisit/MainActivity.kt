package com.pab.modul4_intent_eksplisit

import android.os.Bundle
import android.content.Intent
import android.widget.Button
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

        val btn_pindah = findViewById<Button>(R.id.btn_pindah)
        val btn_pindah_dengan_data = findViewById<Button>(R.id.btn_pindah_dengan_data)
        //pindah activity
        btn_pindah.setOnClickListener {
            val pindah_activity = Intent (this, ActivityKedua::class.java)
            startActivity(pindah_activity)
        }
        //intent dengan data
        btn_pindah_dengan_data.setOnClickListener {
            val pindah_bawa_data = Intent (this, ActivityKedua::class.java)
            pindah_bawa_data.putExtra("Kampus", "Universitas Kuningan")
            pindah_bawa_data.putExtra("Fakultas", "Fakultas Ilmu Komputer")
            pindah_bawa_data.putExtra("Prodi", "Manajemen Informatika")
            pindah_bawa_data.putExtra("Matkul", "Pemrograman Aplikasi Bergerak")
            startActivity(pindah_bawa_data)
        }
    }
}