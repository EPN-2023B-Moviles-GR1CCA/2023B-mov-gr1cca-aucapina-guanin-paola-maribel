package com.example.examenib.model

class Ciudad (var codigoCiudad: Int,
              var nombreCiudad: String,
              var esCapital: Boolean,
              var superficie: Double,
              var seguridad: Char,
              var codigoISOPais: Int
) {
    override fun toString(): String {
        return "Código Ciudad=$codigoCiudad, nombre='$nombreCiudad', esCapital=$esCapital, superficie=$superficie, seguridad=$seguridad, codigoISOPais=$codigoISOPais)"
    }

    fun verificarEsCapital(esCapital: Boolean): String{
        return if (esCapital) "Si" else "No"
    }
}