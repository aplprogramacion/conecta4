package com.apl.conecta4

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencia al botón Jugar
        val playButton = findViewById<Button>(R.id.btn_play)

        // Establecer listener para el botón Jugar
        playButton.setOnClickListener {
            // Iniciar la actividad GameActivity aquí
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        val infobutton = findViewById<Button>(R.id.btn_info)
        infobutton.setOnClickListener{
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}