package com.example.pryIIB_cine

import PeliculaAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : ComponentActivity() {

    private val TAG = "HomeActivity"
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Llamar a una función para cargar películas
        loadMovies()
    }

    private fun loadMovies() {
        val peliculasList = mutableListOf<Pelicula>()
        val peliculasRecyclerView: RecyclerView = findViewById(R.id.peliculasRecyclerView)
        // Configura el LinearLayoutManager con orientación horizontal
        val layoutManager = GridLayoutManager(this, 2) // Ajusta el número de columnas según tus necesidades
        peliculasRecyclerView.layoutManager = layoutManager

        val peliculaAdapter = PeliculaAdapter(peliculasList)
        peliculasRecyclerView.adapter = peliculaAdapter

        val peliculasRef = firestore.collection("peliculas")

        peliculasRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pelicula = Pelicula(
                        document.getString("titulo"),
                        document.getString("director"),
                        document.getString("sinopsis"),
                        document.getString("genero"),
                        document.getString("clasificacion"),
                        document.getString("duracion"),
                        document.getString("elenco"),
                        document.getString("cover")
                    )
                    peliculasList.add(pelicula)
                }

                // Notificar al adaptador que los datos han cambiado
                peliculaAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener películas desde Firestore
                Log.w(TAG, "Error al obtener películas", exception)
            }
    }
}