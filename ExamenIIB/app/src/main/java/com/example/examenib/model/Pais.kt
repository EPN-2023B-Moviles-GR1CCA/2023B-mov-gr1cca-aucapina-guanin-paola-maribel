package com.example.examenib.model

class Pais(
    var codigoISO: Int = 0,
    var nombrePais: String = "",
    var pibPais: Double = 0.0,
    var simboloDinero: String = "",
    var miembroONU: Boolean = false
){
    override fun toString(): String {
        return "País: $nombrePais\n" +
                "Código ISO: $codigoISO\n" +
                "PIB Nominal: $pibPais\n" +
                "Miembro de la ONU: $miembroONU\n" +
                "Símbolo de la moneda local: $simboloDinero"
    }

    fun verificarMiembroONU(miembroONU: Boolean): String{
        return if (miembroONU) "Si" else "No"
    }

}


