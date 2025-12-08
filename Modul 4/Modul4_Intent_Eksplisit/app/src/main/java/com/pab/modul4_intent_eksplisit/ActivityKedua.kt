package com.pab.modul4_intent_eksplisit

import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityKedua : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kedua)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mengambil data dari intent
        var data_univesitas = intent.getStringExtra("Kampus")
        var data_fakultas = intent.getStringExtra("Fakultas")
        var data_prodi = intent.getStringExtra("Prodi")
        var data_matkul = intent.getStringExtra("Matkul")
        //menampilkan data ke layout
        val univ_tampil = findViewById<TextView>(R.id.univ)
        val fakultas_tampil = findViewById<TextView>(R.id.fakultas)
        val prodi_tampil = findViewById<TextView>(R.id.prodi)
        val matkul_tampil = findViewById<TextView>(R.id.matkul)
        //menampilkan data ke layout
        univ_tampil.text = data_univesitas
        fakultas_tampil.text = data_fakultas
        prodi_tampil.text = data_prodi
        matkul_tampil.text = data_matkul
    }
}