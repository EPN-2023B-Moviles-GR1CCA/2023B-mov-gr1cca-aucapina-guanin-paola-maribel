package com.example.proyectoinicial


class BBaseDatosMemoria {


    //companion object
    companion object{
        val arregloBEntrenador= arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Paola","p@p.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Maribel","m@m.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Susana","s@s.com")
                )

        }
    }


}