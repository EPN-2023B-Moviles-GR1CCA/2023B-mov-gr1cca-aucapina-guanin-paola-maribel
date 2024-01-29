package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.examenib.model.Ciudad
import com.example.examenib.model.Pais
import com.google.android.material.snackbar.Snackbar

class Ciudad_Crear : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad_crear)

        val codigoISOPais = intent.extras?.getString("codigoISO")

        /*Definicion del combo box para es ciudad*/
        val spinnerEsCiudad = findViewById<Spinner>(R.id.spEsCiudad)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.itemsONU,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEsCiudad.adapter = adaptador
        /*Fin definicion spinner*/

        /*Creacion de ciudad*/
        val btnGuardarCiudad = findViewById<Button>(R.id.btnGuardarCiudad)
        btnGuardarCiudad
            .setOnClickListener {
                try {
                    val codigoCiudad = findViewById<EditText>(R.id.etxCodCiudad)
                    val nombreCiudad = findViewById<EditText>(R.id.etxNombreCiudad)
                    val superficie = findViewById<EditText>(R.id.etxSuperficieCiudad)
                    val seguridad = findViewById<EditText>(R.id.etxSeguridadCiudad)
                    val esCapital = spinnerEsCiudad.selectedItem.toString()

                    val capital = esCapital.equals("Si")

                    val ciudadNuevo = Ciudad(
                        codigoCiudad.text.toString().toInt(),
                        nombreCiudad.text.toString(),
                        capital,
                        superficie.text.toString().toDouble(),
                        (seguridad.text.toString())[0],
                        codigoISOPais!!.toInt()
                    )

                    val respuesta = db
                        .paisApp!!.crearCiudad(ciudadNuevo)

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
                findViewById(R.id.constraintCiudades), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

}