package com.example.examenib.model

class Pais(
    var codigoISO: Int,
    var nombrePais: String,
    var pibPais: Double,
    var simboloDinero: Char,
    var miembroONU: Boolean
){
    override fun toString(): String {
        return "Código ISO:$codigoISO, Nombre:'$nombrePais', PIB Nominal:$pibPais, Es miembro de la ONU:$miembroONU, Simbolo del la moneda local:$simboloDinero)"
    }

    fun verificarMiembroONU(miembroONU: Boolean): String{
        return if (miembroONU) "Si" else "No"
    }

}


