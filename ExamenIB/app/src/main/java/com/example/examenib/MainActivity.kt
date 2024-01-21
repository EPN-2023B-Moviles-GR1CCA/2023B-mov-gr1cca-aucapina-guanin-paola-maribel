package com.example.examenib

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.examenib.bd.PaisDB
import com.example.examenib.model.Pais
import com.example.examenib.ui.theme.ExamenIBTheme
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var paises= arrayListOf<Pais>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //inicializar bd
        db.Pais= PaisDB(this)

        /*if(paises.size != 0){
            //listado de cocineros
            val listView = findViewById<ListView>(R.id.lv_paises)

            val adaptador = ArrayAdapter(
                this, // contexto
                android.R.layout.simple_list_item_1, // como se va a ver (XML)
                paises
            )

            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
        }else{
            mostrarSnackbar("No existen cocineros")
        }*/




    //funcionalidad boton

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
    //Snackbar
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraintPaises),
                texto,
                Snackbar.LENGTH_LONG

            )
            .show()
    }

}
