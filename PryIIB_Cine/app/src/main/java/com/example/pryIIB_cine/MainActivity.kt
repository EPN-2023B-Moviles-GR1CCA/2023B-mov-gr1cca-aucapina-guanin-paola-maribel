package com.example.pryIIB_cine


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnComenzar=findViewById<Button>(R.id.btnComenzar)
        btnComenzar
            .setOnClickListener{
                val intent = Intent(this, AuthActivity::class.java)
                // Iniciar la nueva actividad
                startActivity(intent)
            }

    }
}
