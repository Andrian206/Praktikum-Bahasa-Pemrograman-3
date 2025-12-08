package com.pab.modul4_intent_parcelable

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InputNo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_no)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNoTelepon: EditText = findViewById(R.id.et_phone_number)
        val btnTelepon: Button = findViewById(R.id.btn_dial)

        btnTelepon.setOnClickListener {
            val NoTelepon = etNoTelepon.text.toString()
            val TeleponIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$NoTelepon"))
            startActivity(TeleponIntent)
        }
    }
}