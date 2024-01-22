package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.examenib.model.Pais
import com.google.android.material.snackbar.Snackbar

class Pais_Crear : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pais_crear)

        //Funcionalidad del Spinner
        val spinnerPaisONU=findViewById<Spinner>(R.id.spEsCiudad)
        val adapter=ArrayAdapter.createFromResource(
            this,
            R.array.itemsONU,
            android.R.layout.simple_spinner_item

        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerPaisONU.adapter=adapter
        //Funcionalidad Boton Crear Pais
        val btnSavePais=findViewById<Button>(R.id.btnGuardarCiudad)
        btnSavePais
            .setOnClickListener{

                try {


                    val codigoISO = findViewById<EditText>(R.id.etxCodCiudad)
                    val nombrePais = findViewById<EditText>(R.id.etxNombreCiudad)
                    val pibPais = findViewById<EditText>(R.id.etxSuperficieCiudad)
                    val simboloDinero = findViewById<EditText>(R.id.etxSeguridadCiudad)
                    val miembroONU = spinnerPaisONU.selectedItem.toString()

                    val miembro = miembroONU.equals("Si")

                    val paisNuevo = Pais(
                        codigoISO.text.toString().toInt(),
                        nombrePais.text.toString(),
                        pibPais.text.toString().toDouble(),
                        (simboloDinero.text.toString())[0],
                        miembro
                    )

                    val respuesta = db
                        .paisApp!!.crearPais(paisNuevo)

                    if(respuesta) {
                        val data = Intent()
                        data.putExtra("mensaje", "Se creo")
                        setResult(RESULT_OK, data)
                        finish()
                    }else{
                        mostrarSnackbar("No se creo")
                    }


                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }
    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraintPaises), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiempo
            )
            .show()
    }
}