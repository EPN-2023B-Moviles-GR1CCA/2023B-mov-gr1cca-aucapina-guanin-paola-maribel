package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar

class Pais_Crear : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pais_crear)

        //Funcionalidad del Spinner
        val spinnerPaisONU=findViewById<Spinner>(R.id.spMiembroONU)
        val adapter=ArrayAdapter.createFromResource(
            this,
            R.array.itemsONU,
            android.R.layout.simple_spinner_item

        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerPaisONU.adapter=adapter
        //Funcionalidad Boton Crear Pais
        val btnSavePais=findViewById<Button>(R.id.btnGuardarPais)
        btnSavePais
            .setOnClickListener{

                try {


                    val codigoISO = findViewById<EditText>(R.id.etxCodIso)
                    val nombrePais = findViewById<EditText>(R.id.etxNombrePais)
                    val pibPais = findViewById<EditText>(R.id.etxPibPais)
                    val simboloDinero = findViewById<EditText>(R.id.etxSimDinero)
                    val miembroONU = spinnerPaisONU.selectedItem.toString()


                    // Limpiar errores anteriores
                    /*codigoUnico.error = null
                    nombre.error = null
                    apellido.error = null
                    edad.error = null
                    fechaContratacion.error = null
                    salario.error = null

                    if(validarCampos(codigoUnico, nombre, apellido, edad, fechaContratacion, salario, isMainChef)){*/
                       /* val esPrincipal = isMainChef.equals("Si")

                        val newChef = Cocinero(
                            codigoUnico.text.toString(),
                            nombre.text.toString(),
                            apellido.text.toString(),
                            edad.text.toString().toInt(),
                            fechaContratacion.text.toString(),
                            salario.text.toString().toDouble(),
                            esPrincipal
                        )

                        val respuesta = db
                            .tablaCocinero!!.crearCocinero(newChef)

                        if(respuesta) {
                            mostrarSnackbar("El cocinero se ha creado exitosamente")
                        }else{
                            mostrarSnackbar("Hubo un problema en la creacion del cocinero")
                        }
                 //   }*/

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