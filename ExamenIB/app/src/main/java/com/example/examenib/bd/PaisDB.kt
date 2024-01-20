package com.example.examenib.bd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.examenib.model.Pais

class PaisDB (
    contexto: Context?, //this
): SQLiteOpenHelper(
    contexto,
    "bd_examenIB",
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val createPaisTableSQL =
            """
                CREATE TABLE IF NOT EXISTS paises (
                    codigoISO INTEGER PRIMARY KEY ON CONFLICT ABORT,
                    nombrePais TEXT,
                    pibPais DOUBLE,
                    simboloDinero CHAR,
                    miembroONU INTEGER
                    
                );
            """. trimIndent()
        db?.execSQL(createPaisTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearPais(nuevoPais: Pais):Boolean{
        val wrBD=writableDatabase
        val saveValues=ContentValues()

        saveValues.put("codigoISO",nuevoPais.codigoISO)
        saveValues.put("nombrePais",nuevoPais.nombrePais)
        saveValues.put("pibPais",nuevoPais.pibPais)
        saveValues.put("simboloDinero",nuevoPais.simboloDinero.toString())
        saveValues.put("miembroONU",if(nuevoPais.miembroONU) 1 else 0)

        val resultSave=wrBD
            .insert(
                "paises",
                null,
                saveValues
            )
        wrBD.close()
        return if(resultSave.toInt()==-1) false else true

    }

    fun readAll(): ArrayList<Pais> {

        val rdBD = readableDatabase

        val selectSQL =
            """
                SELECT * FROM paises
            """.trimIndent()

        val resultSet = rdBD.rawQuery(selectSQL, null)

        val paises = arrayListOf<Pais>()

        if (resultSet != null && resultSet.moveToFirst()) {
            do {
                val codigoISO = resultSet.getInt(0)
                val nombrePais = resultSet.getString(1)
                val pibPais = resultSet.getDouble(2)
                val simboloDinero = resultSet.getString(3)
                val miembroONU = resultSet.equals(1)

                if (codigoISO != null) {
                    val paisEncontrado = Pais(112, "Ecuador", 0.0, '$', true)
                    paisEncontrado.codigoISO = codigoISO
                    paisEncontrado.nombrePais = nombrePais
                    paisEncontrado.pibPais = pibPais
                    paisEncontrado.simboloDinero = simboloDinero[1]
                    paisEncontrado.miembroONU = miembroONU.equals("1")

                    paises.add(paisEncontrado)
                }
            } while (resultSet.moveToNext())
        }
        resultSet?.close()
        rdBD.close()
        return paises
    }

    fun readISO(codigoISO: String): Pais{
        val readBD = readableDatabase

        val selectSQL = """
            SELECT * FROM paises WHERE codigoISO = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(codigoISO)

        val resultSet = readBD.rawQuery(
            selectSQL, //Consulta
            parametrosConsultaLectura //Parametros
        )

        val existePais = resultSet.moveToFirst()

        val paisEncontrado = Pais( 0, "", 0.0, '$',  false)
        if(existePais){
            do{
                val codigoISO = resultSet.getInt(0)
                val nombrePais = resultSet.getString(1)
                val pibPais = resultSet.getDouble(2)
                val simboloDinero = resultSet.getString(3)
                val miembroONU = resultSet.equals(1)

                if(codigoISO!= null){
                    paisEncontrado.codigoISO = codigoISO
                    paisEncontrado.nombrePais = nombrePais
                    paisEncontrado.pibPais = pibPais
                    paisEncontrado.simboloDinero = simboloDinero[1]
                    paisEncontrado.miembroONU = miembroONU.equals("1")
                }
            } while (resultSet.moveToNext())
        }

        resultSet.close()
        readBD.close()
        return paisEncontrado //arreglo
    }

    /*
    fun updatePorISO(
        datosActualizados: Pais
    ): Boolean{
        val conexionEscritura = writableDatabase
        val updateValues = ContentValues()
        valoresAActualizar.put("nombre", datosActualizados.nombre)
        valoresAActualizar.put("apellido", datosActualizados.apellido)
        valoresAActualizar.put("edad", datosActualizados.edad)
        valoresAActualizar.put("fechaContratacion", datosActualizados.fechaContratacion)
        valoresAActualizar.put("salario", datosActualizados.salario)
        valoresAActualizar.put("isMainChef", if(datosActualizados.isMainChef) 1 else 0)
        //where id = ?
        val parametrosConsultaActualizar = arrayOf(datosActualizados.codigoUnico)
        val resultadoActualizcion = conexionEscritura
            .update(
                "COCINERO", //tabla
                valoresAActualizar,
                "codigoUnico = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizcion == -1) false else true
    }

    fun eliminarCocineroPorCodigoUnico(codigoUnico: String): Boolean{
        val conexionEscritura = writableDatabase

        val parametrosConsultaDelete = arrayOf( codigoUnico)

        val resultadoEliminacion = conexionEscritura
            .delete(
                "COCINERO", //tabla
                "codigoUnico = ?",
                parametrosConsultaDelete
            )

        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }
*/


}