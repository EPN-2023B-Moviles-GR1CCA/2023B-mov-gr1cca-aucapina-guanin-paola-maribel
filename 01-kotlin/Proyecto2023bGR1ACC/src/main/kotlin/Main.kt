import java.util.*

fun main() {
    println("Hola mundo")
    //Inmutables (NO se reasignan "=")
    val inmutable:String ="Paola";
    //inmutable="Paola";

    //Mutables (Re asignar)
    var mutable:String="Maribel";
    mutable="Paola";

    //val > var
    //Duck Typing
    var ejemploVariable="Paola Aucapiña"
    val edadEjemplo: Int=12
    ejemploVariable.trim()
    //ejemploVariable=edadEjemplo

    //Variable primitiva
    val nombreProfesor: String="Adrian Eguez"
    val sueldo: Double=1.2
    val estadoCivil: Char='C'
    val mayorEdad: Boolean=true

    //Clases Java
    val fechaNacimiento: Date=Date()

    //Switch
    val estadoCivilWhen ="C"
    when(estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        "S"->{
            println("Soltero")
        }
        else->{
            println("No sabemos")
        }
    }
    val coqueteo=if (estadoCivilWhen=="S") "Si" else "No"


}

//void->Unit
fun imprimirNombre(nombre: String): Unit{
    //"Nombre:"+variable+"bienvenida";
    println("Nombre:${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double=12.00, //opcional (defecto)
    bonoEspecial: Double?=null, //opcion null->nullable
):Double{
    //Int ->Int? (nullable)
    //String ->String? (nullable)
    //Date ->Date? (nullable)

    if (bonoEspecial==null){
        return sueldo*(100/tasa)
    }else{
        bonoEspecial.dec()
    return sueldo*(100/tasa)+bonoEspecial
    }

}
