package com.example.examenib.bd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.examenib.model.Ciudad
import com.example.examenib.model.Pais
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.DocumentSnapshot


class PaisDB {
    // Implementacion Firestore
    val db = Firebase.firestore

    //CRUD Pais

    fun crearPais(nuevoPais: Pais):Task<Void>{
        val documentReference = db.collection("paises").document(nuevoPais.codigoISO.toString())
        return documentReference.set(nuevoPais)
    }

    fun readAll(callback: (ArrayList<Pais>)-> Unit) {
        val paisesList = ArrayList<Pais>()
        db.collection("paises")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pais = document.toObject(Pais::class.java)
                    pais.codigoISO = document.id.toInt()
                    paisesList.add(pais)
                }
                callback(paisesList)
            }
            .addOnFailureListener { exception ->
            }
    }

    fun readISO(codigoISO: String): Task<DocumentSnapshot>{
        val documentReference = db.collection("paises").document(codigoISO)
        return documentReference.get()
    }


    fun updatePorISO(datosActualizados: Pais): Task<Void>{
        val documentReference = db.collection("paises").document(datosActualizados.codigoISO.toString())
        val data = hashMapOf(
            "nombrePais" to datosActualizados.nombrePais,
            "pibPais" to datosActualizados.pibPais,
            "miembroONU" to datosActualizados.miembroONU,
            "simboloDinero" to datosActualizados.simboloDinero
        )
        return documentReference.set(data)
    }

    fun deleteISO(codigoISO: String): Task<DocumentSnapshot>{
        val documentReference = db.collection("paises").document(codigoISO)
        return documentReference.get()
    }



    //CRUD Ciudad

    fun crearCiudad(nuevaCiudad: Ciudad): Task<Void>{
        val paiseDocumentReference = db.collection("paises").document(nuevaCiudad.codigoISOPais.toString())
        val ciudadesCollectionReference = paiseDocumentReference.collection("ciudades")
        val documentReference = ciudadesCollectionReference.document(nuevaCiudad.codigoCiudad.toString())
        return documentReference.set(nuevaCiudad)
    }

    fun obtenerCiudadesPorPais(codigoISOPais: String, callback: (ArrayList<Ciudad>) -> Unit) {
        val ciudades = ArrayList<Ciudad>()
        val collectionReference = db.collection("paises").document(codigoISOPais).collection("ciudades")
        collectionReference.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val ciudad = document.toObject(Ciudad::class.java)
                    ciudad?.codigoCiudad = document.id.toInt()
                    ciudad?.let {
                        ciudades.add(it)
                    }
                }
                callback(ciudades)
            }
    }

    fun consultarCiudadPorCodYPais(codigoCiudad: String, codigoISOPais: String): Task<DocumentSnapshot>{
        val documentReference = db.collection("paises").document(codigoISOPais)
            .collection("ciudades").document(codigoCiudad)
        return documentReference.get()

    }

    fun actualizarCiudadPorCodYPais(datosActualizados: Ciudad): Task<Void>{
        val documentReference = db.collection("paises").document(datosActualizados.codigoISOPais.toString())
            .collection("ciudades").document(datosActualizados.codigoCiudad.toString())
        return documentReference.set(datosActualizados)

    }

    fun eliminarCiudadPorCodEISO(codigoCiudad: String, codigoISOPais: String): Task<Void>{
        val documentReference = db.collection("paises").document(codigoISOPais)
            .collection("ciudades").document(codigoCiudad)
        return documentReference.delete()
    }
}


