package com.example.examenib

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examenib.bd.PaisDB
import com.example.examenib.model.Pais
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var paises= arrayListOf<Pais>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //inicializar bd
        db.paisApp= PaisDB()

        db.paisApp!!.readAll { paises ->
            this.paises = paises

            if(paises.size != 0){

                val listView = findViewById<ListView>(R.id.lv_list_paises)

                val adaptador = ArrayAdapter(
                    this, // contexto
                    android.R.layout.simple_list_item_1, // como se va a ver (XML)
                    paises
                )

                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }else{
                mostrarSnackbar("No existen paises")
            }
        }





        //funcionalidad boton

        val btnCrearPais=findViewById<Button>(R.id.btnCrear)
        btnCrearPais
            .setOnClickListener{
                val intent = Intent(this, Pais_Crear::class.java)
                callbackContenidoIntentExplicito.launch(intent)
            }

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

    //creacion de las opciones de accion (editar, eliminar, ver ciudades)
    var posicionItemSeleccionado = 0
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        //obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar ->{
                val codigoISO = paises.get(posicionItemSeleccionado).codigoISO

                val extras = Bundle()
                extras.putString("codigoISO", codigoISO.toString())
                irActividad(Pais_Editar::class.java, extras)
                return true
            }
            R.id.mi_eliminar -> {

                val result: Boolean = abrirDialogo(paises.get(posicionItemSeleccionado).codigoISO)
                if(result) true else

                    return false
            }
            R.id.mi_ver -> {
                val codigoISO = paises.get(posicionItemSeleccionado).codigoISO

                val extras = Bundle()
                extras.putString("codigoISO", codigoISO.toString())
                irActividad(Ciudad_Ver::class.java, extras)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(codigoISO: Int): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar este pais?")

        var eliminacionExitosa = false

        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->

                db.paisApp?.deleteISO(paises.get(posicionItemSeleccionado).codigoISO.toString())
                    ?.addOnSuccessListener {
                        mostrarSnackbar("Se elimino el pais")
                        cargarPaises()
                        eliminacionExitosa = true
                    }
                    ?.addOnFailureListener { e ->
                        mostrarSnackbar("No se pudo eliminar")
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

    fun irActividad(clase: Class<*>, datosExtras: Bundle? = null) {
        val intent = Intent(this, clase)
        if (datosExtras != null) {
            intent.putExtras(datosExtras)
        }
//        startActivity(intent)
        callbackContenidoIntentExplicito.launch(intent)
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    cargarPaises()
                    mostrarSnackbar("${data?.getStringExtra("mensaje")}")
                }
            }
        }

    private fun cargarPaises() {

        db.paisApp!!.readAll { paises ->
            this.paises = paises

            val adaptador = ArrayAdapter(
                this, // contexto
                android.R.layout.simple_list_item_1, // como se va a ver (XML)
                paises
            )
            val listView = findViewById<ListView>(R.id.lv_list_paises)
            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
            }
    }

}



