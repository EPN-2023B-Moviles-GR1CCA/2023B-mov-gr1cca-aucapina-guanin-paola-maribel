package com.example.proyectoinicial

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "moviles", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
               CREATE TABLE ENTRENADOR(
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               descripcion VARCHAR(50)
               ) 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?,
                           p1: Int,
                           p2: Int) {

    }

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        // [1]
        // [1,2,3], "id=? and a=? and b=?", "id=1 and a=2 and b=3"
        // ? = 1
        // "id=?" => "id=1"
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }


}