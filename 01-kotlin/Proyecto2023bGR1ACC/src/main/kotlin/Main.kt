import java.util.*
import kotlin.collections.ArrayList

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

    //If-else
    val coqueteo=if (estadoCivilWhen=="S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)

    //  Parametros nombrados
    calcularSueldo(sueldo = 10.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00)
    calcularSueldo(sueldo = 10.00, tasa = 12.00, bonoEspecial = 20.00)

    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00) //Named parameters
    calcularSueldo(10.00, bonoEspecial = 20.00) //Named parameters

    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //Named parameters(se puede poner en cualquier orden siempre que se nombren)


    //Instanciar la clase Suma
    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)

    val sumaCuatro = Suma(null,null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos

    //Tipos de arreglos

    //Arreglo estático
    val arregLoEstático: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregLoEstático)


    //Arreglo dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    //For each->Unit
    //Iterar arreglo
    val respuestaForEach: Unit=arregloDinamico
        .forEach{valorActual:Int->
            println("valor actual: ${valorActual}")
        }
    //it (eso) significa el elemento iterativo
    arregloDinamico.forEach({ println(it) })

    arregLoEstático
        .forEachIndexed{indice: Int,valorActual:Int->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //MAP-> muta el arreglo(cambia el arreglo)
    //1.enviamos el nuevo valor de la iteracion
    //2.devuelve un NUEVO ARREGLO con valores
    //modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual:Int->
            return@map valorActual.toDouble()+100.00
        }
    println(respuestaMap)
    val respuestaMapDos=arregloDinamico.map { it+15 }

    //Filter->filtrar arreglo
    //1.devuelve una expresion (true o false)
    //2.nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int->
            //expresion condicion
            val mayoresAcinco: Boolean=valorActual>5
            return@filter mayoresAcinco
        }

    val respuestaFilterDos=arregloDinamico.filter { it<=5 }

    println(respuestaFilter)
    println(respuestaFilterDos)


}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){ //Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}


abstract class Numeros( //Constructor PRIMARIO
    // Ejemplo:
    // unoProp: Int, //(Parametro (sin modificador de acceso))
    // private var uno: Int, //Propiedad Publica Clase numeros.uno
    // var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
    // public var uno: Int,
    // Propiedad de la clase protected numeros.numeroUno
    protected val numeroUno: Int,
    // Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,
){
    // var cedula: string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init { //Bloque codigo constructor primario
        this.numeroUno; this.numeroDos; // "this" es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}


class Suma( //constructor primario suma
    uno: Int, //parametro
    dos: Int //parametro
): Numeros(uno, dos) { //<- Constructor del Padre
    init { //bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor(//constructor secundario
        uno: Int?, //parametros
        dos: Int //parametros
    ) : this(  //llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor(//tercer constructor
        uno: Int, //parametros
        dos: Int? //parametros
    ) : this(  //llamada constructor primario
        uno,
        if (dos == null) 0 else uno
    )
    //Si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor(//  cuarto constructor
        uno: Int?, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )

    //funciones publicas o privadas

    public fun sumar():Int{ // public por defecto, o usar private
        val total=numeroUno+numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{//atributos y metodos compartidos
        //entre las instancias
        val pi=3.14
        fun elevarAlCuadrado(num: Int):Int{
            return num*num
        }
        val historialSumas= arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }

    }
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
