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
import com.example.examenib.model.Ciudad
import com.google.android.material.snackbar.Snackbar

class Ciudad_Editar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad_editar)
        val codigoISOPais = intent.extras?.getString("codigoISOPais")
        val identificadorComida = intent.extras?.getString("codigoCiudad")
        val nombre_ciudad = intent.extras?.getString("nombreCiudad")

        //Definicion del combo box para si es ciudad
        val spinnerEsCiudad= findViewById<Spinner>(R.id.spEsCiudad)

        val adaptador = ArrayAdapter.createFromResource(
            this, // contexto,
            R.array.itemsONU,
            android.R.layout.simple_spinner_item // como se va a ver (XML).
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerEsCiudad.adapter = adaptador
        //fin spinner
        findViewById<TextView>(R.id.txtNombreCiudad).setText("Ciudad a modificar: $nombre_ciudad")

        mostrarSnackbar(identificadorComida.toString() + "  " + codigoISOPais)
        if (identificadorComida != null && codigoISOPais != null) {
            db.paisApp!!.consultarCiudadPorCodYPais(identificadorComida, codigoISOPais.toString())
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val ciudadEdicion = documentSnapshot.toObject(Ciudad::class.java)


                        val codigoCiudad = findViewById<EditText>(R.id.etxCodCiudad)
                        val superficie = findViewById<EditText>(R.id.etxSuperficieCiudad)
                        val seguridad = findViewById<EditText>(R.id.etxSeguridadCiudad)
                        val esCapital = spinnerEsCiudad.selectedItem.toString()


                        codigoCiudad.setText(documentSnapshot.id)
                        superficie.setText(ciudadEdicion!!.superficie.toString())
                        seguridad.setText(ciudadEdicion.seguridad.toString())


                        // Configura el Spinner con el valor de es ciudad
                        val esCapitalArray = resources.getStringArray(R.array.itemsONU)

                        val esCapitalPosition = if (ciudadEdicion.esCapital) {
                            esCapitalArray.indexOf("Si")
                        } else {
                            esCapitalArray.indexOf("No")
                        }
                        spinnerEsCiudad.setSelection(esCapitalPosition)
                    }
                    else{


                    }
                }.addOnFailureListener {
                    exception->
                }

        }

        //Edicion de ciudad
        val btnActualizarCiudad= findViewById<Button>(R.id.btnGuardarCiudad)
        btnActualizarCiudad
            .setOnClickListener {
                try {
                    val codigoCiudad = findViewById<EditText>(R.id.etxCodCiudad)
//                  val nombreCiudad = findViewById<EditText>(R.id.etxNombreCiudad)
                    val superficie = findViewById<EditText>(R.id.etxSuperficieCiudad)
                    val seguridad = findViewById<EditText>(R.id.etxSeguridadCiudad)
                    val esCapital = spinnerEsCiudad.selectedItem.toString()

                    val capital = esCapital.equals("Si")

                    val ciudadNuevo = Ciudad(
                        codigoCiudad.text.toString().toInt(),
                        nombre_ciudad.toString(),
                        capital,
                        superficie.text.toString().toDouble(),
                        (seguridad.text.toString()),
                        codigoISOPais!!.toInt()
                    )


                    db.paisApp?.actualizarCiudadPorCodYPais(ciudadNuevo)
                        ?.addOnSuccessListener {
                            val data = Intent()
                            data.putExtra("codigoISOPais", codigoISOPais)
                            data.putExtra("message", "Se creo")
                            setResult(RESULT_OK, data)
                            finish()
                        }
                        ?.addOnFailureListener { e ->
                            mostrarSnackbar("No se creo")

                        }

                } catch (e: Exception) {
                    Log.e("Error", "Error en la aplicación", e)
                }
            }
    }

    fun mostrarSnackbar(texto: String) {
        Snackbar
            .make(
                findViewById(R.id.constraintCiudadEditar), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }
}
