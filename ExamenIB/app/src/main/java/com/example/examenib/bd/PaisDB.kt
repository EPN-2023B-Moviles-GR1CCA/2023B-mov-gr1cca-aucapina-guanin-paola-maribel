package com.example.examenib.bd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.examenib.model.Ciudad
import com.example.examenib.model.Pais

class PaisDB  (
    contexto: Context?, //this
): SQLiteOpenHelper(
    contexto,
    "db_examenIB",
    null,
    5
){
    override fun onCreate(db: SQLiteDatabase?) {
        //creacion tabla paises
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
        //Creacion tabla ciudades
        val createCiudadTableSQL =
            """
                CREATE TABLE IF NOT EXISTS ciudades (
                    codigoCiudad INTEGER PRIMARY KEY ON CONFLICT ABORT,
                    nombreCiudad TEXT,
                    esCapital INTEGER,
                    superficie DOUBLE,
                    seguridad CHAR,
                    codigoISOPais INTEGER,
                    CONSTRAINT fk_paises
                    FOREIGN KEY (codigoISOPais)
                    REFERENCES paises(codigoISO)
                    ON DELETE CASCADE
                    
                );
            """. trimIndent()
        db?.execSQL(createCiudadTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 5) {
            // Elimina las tablas si existen
            db?.execSQL("DROP TABLE IF EXISTS ciudades")
            db?.execSQL("DROP TABLE IF EXISTS paises")

            // Vuelve a crear la tabla paises
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

            // Vuelve a crear la tabla ciudades con ON DELETE CASCADE
            val createCiudadTableSQL =
                """
                CREATE TABLE IF NOT EXISTS ciudades (
                    codigoCiudad INTEGER PRIMARY KEY ON CONFLICT ABORT,
                    nombreCiudad TEXT,
                    esCapital INTEGER,
                    superficie DOUBLE,
                    seguridad CHAR,
                    codigoISOPais INTEGER,
                    CONSTRAINT fk_paises
                    FOREIGN KEY (codigoISOPais)
                    REFERENCES paises(codigoISO)
                    ON DELETE CASCADE
                    
                );
            """. trimIndent()
            db?.execSQL(createCiudadTableSQL)

        }

    }

    //CRUD Pais

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
                val miembroONU = resultSet.getString(4)

                if (codigoISO != null) {
                    val paisEncontrado = Pais(112, "Ecuador", 0.0, '$', true)
                    paisEncontrado.codigoISO = codigoISO
                    paisEncontrado.nombrePais = nombrePais
                    paisEncontrado.pibPais = pibPais
                    paisEncontrado.simboloDinero = simboloDinero[0]
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
                val miembroONU = resultSet.getString(4)

                if(codigoISO!= null){
                    paisEncontrado.codigoISO = codigoISO
                    paisEncontrado.nombrePais = nombrePais
                    paisEncontrado.pibPais = pibPais
                    paisEncontrado.simboloDinero = simboloDinero[0]
                    paisEncontrado.miembroONU = miembroONU.equals("1")
                }
            } while (resultSet.moveToNext())
        }

        resultSet.close()
        readBD.close()
        return paisEncontrado //arreglo
    }


    fun updatePorISO(
        datosActualizados: Pais
    ): Boolean{
        val conexionEscritura = writableDatabase
        val updateValues = ContentValues()
        updateValues.put("nombrePais", datosActualizados.nombrePais)
        updateValues.put("pibPais", datosActualizados.pibPais)
        updateValues.put("simboloDinero", datosActualizados.simboloDinero.toString())
        updateValues.put("miembroONU", if(datosActualizados.miembroONU) 1 else 0)

        //where id = ?
        val parametrosConsultaActualizar = arrayOf(datosActualizados.codigoISO.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "paises", //tabla
                updateValues,
                "codigoISO = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun deleteISO(codigoISO: Int): Boolean{
        val conexionEscritura = writableDatabase

        val parametrosConsultaDelete = arrayOf(codigoISO.toString())

        val resultadoEliminacion = conexionEscritura
            .delete(
                "paises", //tabla
                "codigoISO = ?",
                parametrosConsultaDelete
            )

        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    //CRUD Ciudad
    fun crearCiudad(nuevaCiudad: Ciudad): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("codigoCiudad", nuevaCiudad.codigoCiudad)
        valoresAGuardar.put("nombreCiudad", nuevaCiudad.nombreCiudad)
        valoresAGuardar.put("esCapital" , if(nuevaCiudad.esCapital) 1 else 0)
        valoresAGuardar.put("seguridad", nuevaCiudad.seguridad.toString())
        valoresAGuardar.put("codigoISOPais", nuevaCiudad.codigoISOPais)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "ciudades", //nombre de la tabla
                null,
                valoresAGuardar //valores
            )

        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun obtenerCiudadesPorPais(codigoISOPais: String): ArrayList<Ciudad> {
        val ciudades = arrayListOf<Ciudad>()
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM ciudades WHERE codigoISOPais = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(codigoISOPais)
        Log.d("ConsultaSQL", scriptConsultaLectura)

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura //Parametros
        )

        if(resultadoConsultaLectura != null && resultadoConsultaLectura.moveToFirst()){

            do{
                val codigoCiudad = resultadoConsultaLectura.getInt(0)
                val nombreCiudad = resultadoConsultaLectura.getString(1)
                val esCapital = resultadoConsultaLectura.getString(2)
                val superficie = resultadoConsultaLectura.getDouble(3)
                val seguridad = resultadoConsultaLectura.getString(4)
                val codigoISOPais= resultadoConsultaLectura.getInt(5)

                if(codigoCiudad != null){
                    val ciudadEncontrada = Ciudad(1, "Ambato", true, 12.0, 'A', 112)
                    ciudadEncontrada.codigoCiudad = codigoCiudad
                    ciudadEncontrada.nombreCiudad= nombreCiudad
                    ciudadEncontrada.esCapital = esCapital.equals("1")
                    ciudadEncontrada.superficie = superficie
                    ciudadEncontrada.seguridad = seguridad[0]
                    ciudadEncontrada.codigoISOPais = codigoISOPais

                    ciudades.add(ciudadEncontrada)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura?.close()
        baseDatosLectura.close()

        return ciudades //arreglo
    }

    fun consultarCiudadPorCodYPais(codigoCiudad: String, codigoISOPais: String): Ciudad{
        val baseDatosLectura = readableDatabase

        val scriptConsultaLectura = """
            SELECT * FROM ciudades WHERE codigoCiudad = ? AND codigoISOPais = ?
        """.trimIndent()

        val parametrosConsultaLectura = arrayOf(codigoCiudad, codigoISOPais)

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, //Consulta
            parametrosConsultaLectura //Parametros
        )

        val existePais = resultadoConsultaLectura.moveToFirst()

        val ciudadEncontrada = Ciudad(0,"",false,0.0,'A',0)
        if(existePais){
            do{
                val codigoCiudad = resultadoConsultaLectura.getInt(0)
                val nombreCiudad = resultadoConsultaLectura.getString(1)
                val esCapital = resultadoConsultaLectura.getString(2)
                val superficie = resultadoConsultaLectura.getDouble(3)
                val seguridad = resultadoConsultaLectura.getString(4)
                val codigoISOPais= resultadoConsultaLectura.getInt(5)
               if(codigoCiudad!= null){
                   ciudadEncontrada.codigoCiudad = codigoCiudad
                   ciudadEncontrada.nombreCiudad= nombreCiudad
                   ciudadEncontrada.esCapital = esCapital.equals("1")
                   ciudadEncontrada.superficie = superficie
                   ciudadEncontrada.seguridad = seguridad[0]
                   ciudadEncontrada.codigoISOPais = codigoISOPais
                }
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return ciudadEncontrada//arreglo
    }

    fun actualizarCiudadPorCodYPais(
        datosActualizados: Ciudad
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("codigoCiudad", datosActualizados.codigoCiudad)
        valoresAActualizar.put("nombreCiudad", datosActualizados.nombreCiudad)
        valoresAActualizar.put("esCapital", if(datosActualizados.esCapital) 1 else 0)
        valoresAActualizar.put("superficie", datosActualizados.superficie)
        valoresAActualizar.put("seguridad", datosActualizados.seguridad.toString())
        valoresAActualizar.put("codigoISOPais", datosActualizados.codigoISOPais)
        //where id = ?
        val parametrosConsultaActualizar = arrayOf(datosActualizados.codigoCiudad.toString(), datosActualizados.codigoISOPais.toString())
        val resultadoActualizcion = conexionEscritura
            .update(
                "ciudades",//tabla
                valoresAActualizar,
                "codigoCiudad = ? and codigoISOPais = ?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadoActualizcion == -1) false else true
    }

    fun eliminarCiudadPorCodEISO(codigoComida: String, codigoUnico: String): Boolean{
        val conexionEscritura = writableDatabase

        val parametrosConsultaDelete = arrayOf( codigoComida, codigoUnico)

        val resultadoEliminacion = conexionEscritura
            .delete(
                "ciudades", //tabla
                "codigoCiudad = ? and codigoISOPais = ?",
                parametrosConsultaDelete
            )

        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }




}


