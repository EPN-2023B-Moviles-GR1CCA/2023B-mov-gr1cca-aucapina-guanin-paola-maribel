package com.example.pryIIB_cine

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.io.IOException
import java.io.InputStream
import java.net.URL



class DetallesPeliculaActivity: ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_pelicula)

        // Recuperar datos de la película seleccionada
        val titulo = intent.getStringExtra("titulo")
        val director = intent.getStringExtra("director")
        val portada = intent.getStringExtra("portada")
        val sinopsis=intent.getStringExtra("sinopsis")
        // Recupera otros detalles según sea necesario

        // Mostrar los detalles en la interfaz de usuario
        val tituloTextView: TextView = findViewById(R.id.tituloDetallesTextView)
        val directorTextView: TextView = findViewById(R.id.directorDetallesTextView)
        val portadaImageView: ImageView = findViewById(R.id.portadaDetallesImageView)
        val sinopsisTextView: TextView = findViewById(R.id.sinopsisDetallesTextView)

        // Muestra otros detalles según sea necesario
        with(tituloTextView) { text = titulo }
        with(directorTextView) { text = director }
        with(sinopsisTextView) { text = sinopsis }
        // Cargar la imagen directamente desde la URL
        try {
            val inputStream: InputStream = URL(portada).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            portadaImageView.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            // Manejar el error al cargar la imagen
        }

        // Configurar botones
        val continuarButton: Button = findViewById(R.id.continuarButton)

        continuarButton.setOnClickListener {
            // Lógica para continuar con la película (puedes iniciar otra actividad aquí)
            val funcionesIntent = Intent(this, FuncionesActivity::class.java)
            startActivity(funcionesIntent)
        }

    }
}