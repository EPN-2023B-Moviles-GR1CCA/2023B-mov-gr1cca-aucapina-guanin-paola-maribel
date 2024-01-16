package com.example.examenib

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.examenib.ui.theme.ExamenIBTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        val btnCrearPais=findViewById<Button>(R.id.btnCrear)
        btnCrearPais
            .setOnClickListener{
                irActividad(Pais_Crear::class.java)
            }

    }
    
    fun irActividad(clase: Class<*>){
        val intent=Intent(this, clase)
        startActivity(intent)

    }


}
