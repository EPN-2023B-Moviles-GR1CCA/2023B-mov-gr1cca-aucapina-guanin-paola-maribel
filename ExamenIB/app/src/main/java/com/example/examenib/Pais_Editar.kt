package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.examenib.model.Pais
import com.google.android.material.snackbar.Snackbar

class Pais_Editar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pais_editar)

        /*Definicion del combo box para miembro onu*/
        val spinnerPaisONU = findViewById<Spinner>(R.id.spEsCiudad)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.itemsONU,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerPaisONU.adapter = adaptador
        /*Fin definicion spinner*/

        val codigoISOPaisEditar = intent.extras?.getString("codigoISO")
        val nombre_pais = intent.extras?.getString("nombrePais")
        findViewById<TextView>(R.id.txtNombreCiudad).setText(nombre_pais)

        if(codigoISOPaisEditar != null){
            mostrarSnackbar(codigoISOPaisEditar)
            val paisEdicion = db.paisApp!!.readISO(codigoISOPaisEditar)
            mostrarSnackbar(paisEdicion.miembroONU.toString())
            val codigoISO = findViewById<EditText>(R.id.etxCodCiudad)
            val nombrePais = findViewById<EditText>(R.id.etxNombreCiudad)
            val pibPais = findViewById<EditText>(R.id.etxSuperficieCiudad)
            val simboloDinero = findViewById<EditText>(R.id.etxSeguridadCiudad)
            val miembroONU = spinnerPaisONU.selectedItem.toString()

           // val miembro = miembroONU.equals("Si")

            codigoISO.setText(paisEdicion.codigoISO.toString())
            nombrePais.setText(paisEdicion.nombrePais)
            pibPais.setText(paisEdicion.pibPais.toString())
            simboloDinero.setText(paisEdicion.simboloDinero.toString())
            // Configura el Spinner con el valor de isMainChef
            val miembroONUArray = resources.getStringArray(R.array.itemsONU)

            val miembroONUPosition = if (paisEdicion.miembroONU) {
                miembroONUArray.indexOf("Si")
            } else {
                miembroONUArray.indexOf("No")
            }

            spinnerPaisONU.setSelection(miembroONUPosition)

        }

        //Funcionalidad Boton Editar Pais
        val btnActualizarPais = findViewById<Button>(R.id.btnActualizarPais)
        btnActualizarPais
            .setOnClickListener {
                try {
                    val codigoISO = findViewById<EditText>(R.id.etxCodCiudad)
                    val nombrePais = findViewById<EditText>(R.id.etxNombreCiudad)
                    val pibPais = findViewById<EditText>(R.id.etxSuperficieCiudad)
                    val simboloDinero = findViewById<EditText>(R.id.etxSeguridadCiudad)
                    val miembroONU = spinnerPaisONU.selectedItem.toString()

                    val miembro = miembroONU.equals("Si")

                    val updateData = Pais(
                        codigoISO.text.toString().toInt(),
                        nombrePais.text.toString(),
                        pibPais.text.toString().toDouble(),
                        (simboloDinero.text.toString())[0],
                        miembro
                    )

                    val respuesta = db
                        .paisApp!!.updatePorISO(updateData)

                    if(respuesta) {
                        val data = Intent()
                        data.putExtra("mensaje", "Se editó el pais")
                        setResult(RESULT_OK, data)
                        finish()
                    }else{
                        mostrarSnackbar("No se actualizó el pais")
                    }



                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.constraintPaisEditar), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

}