package com.example.examenib.model

class Ciudad (var codigoCiudad: Int,
              var nombreCiudad: String,
              var esCapital: Boolean,
              var superficie: Double,
              var seguridad: Char,
              var codigoISOPais: Int
) {
    override fun toString(): String {
        return "Ciudad: $nombreCiudad\n" +
                "Código: $codigoCiudad\n" +
                "Es Capital: $esCapital\n" +
                "Superficie: $superficie\n" +
                "Nivel de Seguridad: $seguridad\n" +
                "Código ISO del País: $codigoISOPais"
    }

    fun verificarEsCapital(esCapital: Boolean): String{
        return if (esCapital) "Si" else "No"
    }
}