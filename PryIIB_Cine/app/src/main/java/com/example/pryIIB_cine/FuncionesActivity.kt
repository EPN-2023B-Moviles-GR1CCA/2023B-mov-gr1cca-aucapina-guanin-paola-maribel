// FuncionesActivity.kt
package com.example.pryIIB_cine

import FuncionAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class FuncionesActivity : ComponentActivity() {

    private val TAG = "FuncionesActivity"
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funciones)

        // Llamar a una función para cargar funciones
        loadFunciones()
    }

    private fun loadFunciones() {
        val funcionesList = mutableListOf<Funcion>()
        val funcionesRecyclerView: RecyclerView = findViewById(R.id.funcionesRecyclerView)
        val funcionAdapter = FuncionAdapter(funcionesList)
        funcionesRecyclerView.adapter = funcionAdapter
        funcionesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Aquí puedes ajustar la referencia a tu colección y documentos específicos
        val salaId = "V2FHWshrIWIjSz0PFkuG"  // Reemplaza con el ID de la sala específica
        val funcionesRef = firestore.collection("sala").document(salaId).collection("funciones")

        funcionesRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    // Aquí debes adaptar la creación de objetos Funcion según tu modelo de datos
                    val funcion = Funcion(
                        document.getString("nomSala"),
                        document.getString("tipo"),
                        document.getString("horario"),
                        document.getString("idioma")
                        // Agrega otros campos según tu modelo
                    )
                    funcionesList.add(funcion)
                }

                // Notificar al adaptador que los datos han cambiado
                funcionAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener funciones desde Firestore
                Log.w(TAG, "Error al obtener funciones", exception)
            }
    }
}
