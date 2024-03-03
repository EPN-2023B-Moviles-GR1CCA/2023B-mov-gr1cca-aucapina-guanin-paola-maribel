package com.example.examenib.model

class Ciudad (var codigoCiudad: Int = 0,
              var nombreCiudad: String = "",
              var esCapital: Boolean= false,
              var superficie: Double= 0.0,
              var seguridad: String = "" ,
              var codigoISOPais: Int=0
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