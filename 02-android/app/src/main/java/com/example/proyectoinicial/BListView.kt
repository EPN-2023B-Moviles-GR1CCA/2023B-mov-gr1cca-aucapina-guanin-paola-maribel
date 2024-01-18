package com.example.proyectoinicial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this, // Contexto
            // como se va a ver (XML)
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadirListView = findViewById<Button>(
            R.id.btn_añadir_list_view)
        botonAnadirListView
            .setOnClickListener {
                anadirEntrenador(adaptador)
            }

    }// fin onCreate
    fun anadirEntrenador( adaptador: ArrayAdapter<BEntrenador>){
        arreglo.add(
            BEntrenador(1,"Paola","Descripcion")
        )
        adaptador.notifyDataSetChanged()
    }

}