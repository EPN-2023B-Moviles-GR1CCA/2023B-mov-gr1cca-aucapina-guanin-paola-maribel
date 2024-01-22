package com.example.examenib

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.examenib.bd.PaisDB
import com.example.examenib.model.Ciudad
import com.google.android.material.snackbar.Snackbar


class Ciudad_Ver : AppCompatActivity() {
    var ciudades = arrayListOf<Ciudad>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad_ver)

        //inicializar bd
        db.paisApp= PaisDB(this)

        val codigo_ISO = intent.extras?.getString("codigoISO")
        val nombre_pais = intent.extras?.getString("nombrePais")

       // findViewById<TextView>(R.id.txt_titulo_nombre_pais).setText(nombre_pais)

        if(codigo_ISO != null){
            ciudades = db.paisApp!!.obtenerCiudadesPorPais(codigo_ISO)
            if(ciudades.size != 0){
                //listado de ciudades
                val listView = findViewById<ListView>(R.id.lv_ciudades_ver)

                val adaptador = ArrayAdapter(
                    this, // contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    ciudades
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }else{
                mostrarSnackbar("No existen ciudades")
            }

            val btnCrearCiudad = findViewById<Button>(R.id.btn_ciudad_crear)
            btnCrearCiudad
                .setOnClickListener {
                    val extras = Bundle()
                    extras.putString("codigoISO", codigo_ISO)
                    irActividad(Ciudad_Crear::class.java, extras)
                }
        }

    }

    fun mostrarSnackbar(texto:String){
        Snackbar
            .make(
                findViewById(R.id.lv_list_ciudades), //view
                texto, //texto
                Snackbar.LENGTH_LONG //tiwmpo
            )
            .show()
    }

    fun irActividad(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }
//        startActivity(intent)
        callbackContenidoIntentExplicito.launch(intent)
    }

    //creacion de las opciones de accion (editar, eliminar)
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_ciudades, menu)
        //obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_ciudad ->{
                val codigoCiudad = ciudades.get(posicionItemSeleccionado).codigoCiudad
                val nombreCiudad= ciudades.get(posicionItemSeleccionado).nombreCiudad
                val esCapital= ciudades.get(posicionItemSeleccionado).esCapital
                val superficie= ciudades.get(posicionItemSeleccionado).superficie
                val seguridad= ciudades.get(posicionItemSeleccionado).seguridad
                val codigoISOPais= ciudades.get(posicionItemSeleccionado).codigoISOPais

//                mostrarSnackbar(identificador)
                val extras = Bundle()

                extras.putInt("codigoCiudad", codigoCiudad)
                extras.putString("nombreCiudad", nombreCiudad)
                extras.putBoolean("esCapital", esCapital)
                extras.putDouble("superficie", superficie)
                extras.putChar("seguridad", seguridad)
                extras.putInt("codigoISOPais", codigoISOPais)
                irActividad(Ciudad_Editar::class.java, extras)
                return true
            }
            R.id.mi_eliminar_ciudad -> {
                val codigoCiudad = ciudades.get(posicionItemSeleccionado).codigoCiudad
                val codigoISOPais = ciudades.get(posicionItemSeleccionado).codigoISOPais
//                mostrarSnackbar(identificador)
                val result: Boolean = abrirDialogo(codigoCiudad.toString(), codigoISOPais.toString())
                if(result) true else

                    return false
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(codigoCiudad: String, codigoISO: String): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar esta ciudad?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                val respuesta = db.paisApp?.eliminarCiudadPorCodEISO(codigoCiudad.toString(), codigoISO.toString())

                if (respuesta == true) {
                    mostrarSnackbar("Comida eliminado exitosamente")
                    cargarListaCiudades(codigoISO)
                    eliminacionExitosa = true
                } else {
                    mostrarSnackbar("No se pudo eliminar esta comida")
                    eliminacionExitosa = false
                }
            }
        )
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()

        return eliminacionExitosa
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    cargarListaCiudades("${data?.getStringExtra("codigoCocinero")}")
                    mostrarSnackbar("${data?.getStringExtra("message")}")
                }
            }
        }

    private fun cargarListaCiudades(codigoISO: String) {
        // Cargar la lista de ciudades del pais desde la base de datos y notificar al adaptador
        ciudades = db.paisApp!!.obtenerCiudadesPorPais(codigoISO)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            ciudades
        )
        val listView = findViewById<ListView>(R.id.lv_list_ciudades)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }


}