import org.sqlite.SQLiteDataSource
import java.util.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
data class Pais(
    val codigoISO: Int,
    val nombrePais: String,
    val pibPais: Double,
    val miembroONU: Boolean,
    val simboloDinero: Char,
)

data class Ciudad(
    val codigoCiudad: Int,
    val nombreCiudad: String,
    val esCapital: Boolean,
    val superficie: Double,
    val seguridad: Char,
    val codigoISOPais: Int
)

class VerificarPais {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:pais.db"
        crearTablaPais()
    }

    private fun crearTablaPais() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS paises (
                    codigoISO INTEGER PRIMARY KEY,
                    nombrePais TEXT,
                    pibPais DOUBLE,
                    miembroONU BOOLEAN,
                    simboloDinero CHAR
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existePais(codigoISO: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM paises WHERE codigoISO = ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, codigoISO)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun create(pais: Pais) {
        if (existePais(pais.codigoISO)) {
            println("El codigo ISO del pais ya existe.")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO paises(codigoISO, nombrePais, pibPais, miembroONU, simboloDinero)
                VALUES (?, ?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, pais.codigoISO)
                preparedStatement.setString(2, pais.nombrePais)
                preparedStatement.setDouble(3, pais.pibPais)
                preparedStatement.setBoolean(4, pais.miembroONU)
                preparedStatement.setString(5, pais.simboloDinero.toString())
                preparedStatement.executeUpdate()
            }
        }
        println("Pais creado correctamente.")
    }

    fun readAll(): List<Pais> {
        val paises = mutableListOf<Pais>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM paises"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    paises.add(
                        Pais(
                            resultSet.getInt("codigoISO"),
                            resultSet.getString("nombrePais"),
                            resultSet.getDouble("pibPais"),
                            resultSet.getBoolean("miembroONU"),
                            resultSet.getString("simboloDinero")[0]

                        )
                    )
                }
            }
        }
        return paises
    }

    fun update(pais: Pais) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE paises
                SET nombrePais = ?, pibPais = ?, miembroONU = ?, simboloDinero = ?
                WHERE codigoISO = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setString(1, pais.nombrePais)
                preparedStatement.setDouble(2, pais.pibPais)
                preparedStatement.setBoolean(3, pais.miembroONU)
                preparedStatement.setString(4, pais.simboloDinero.toString())
                preparedStatement.setInt(5, pais.codigoISO)
                preparedStatement.executeUpdate()
            }
        }
        println("Pais actualizado correctamente.")
    }

    fun delete(codigoISO: Int) {
        dataSource.connection.use { connection ->


            // Eliminar ciudades asociadas al país
            val deleteCiudadesSQL = "DELETE FROM ciudades WHERE codigoISOPais = ?"
            connection.prepareStatement(deleteCiudadesSQL).use { preparedStatement ->
                preparedStatement.setInt(1, codigoISO)
                preparedStatement.executeUpdate()
            }

            // Eliminar el país
            val deletePaisSQL = "DELETE FROM paises WHERE codigoISO = ?"
            connection.prepareStatement(deletePaisSQL).use { preparedStatement ->
                preparedStatement.setInt(1, codigoISO)
                preparedStatement.executeUpdate()
            }
        }
        println("Pais eliminado.")
    }


    fun exportPaisesToCSV(filename: String) {
        val paises = readAll()
        val file = File(filename)
        try {
            FileWriter(file).use { writer ->
                writer.append("CodigoISO,NombrePais,PibPerCápitaPais,MiembroONU,SimboloDinero\n")
                paises.forEach { pais ->
                    writer.append("${pais.codigoISO},${pais.nombrePais},${pais.pibPais},${pais.miembroONU}," +
                            "${pais.simboloDinero}\n")
                }
            }
            println("Datos de países exportados correctamente a $filename")
        } catch (e: IOException) {
            println("Error al exportar datos de países a $filename")
            e.printStackTrace()
        }
    }
}

//Creacion de la tabla ciudades
class verificarCiudad {
    private val dataSource: SQLiteDataSource

    init {
        dataSource = SQLiteDataSource()
        dataSource.url = "jdbc:sqlite:pais.db"
        crearTablaCiudad()
    }

    private fun crearTablaCiudad() {
        dataSource.connection.use { connection ->
            val statement = connection.createStatement()
            val createTableSQL = """
                CREATE TABLE IF NOT EXISTS ciudades (
                    codigoCiudad INTEGER PRIMARY KEY,
                    nombreCiudad TEXT,
                    esCapital BOOLEAN,
                    superficie DOUBLE,
                    seguridad DATE,
                    codigoISOPais INTEGER,
                    FOREIGN KEY (codigoISOPais) REFERENCES paises(codigoISO)
                );
            """
            statement.executeUpdate(createTableSQL)
        }
    }

    fun existeCiudad(codigoCiudad: Int): Boolean {
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT COUNT(*) FROM ciudades WHERE codigoCiudad= ?"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                preparedStatement.setInt(1, codigoCiudad)
                val resultSet = preparedStatement.executeQuery()
                return resultSet.getInt(1) > 0
            }
        }
    }

    fun create(ciudad: Ciudad, codigoISOPais: Int) {
        if (existeCiudad(ciudad.codigoCiudad)) {
            println("El codigo de la ciudad ya existe.")
            return
        }
        dataSource.connection.use { connection ->
            val insertSQL = """
                INSERT INTO ciudades (codigoCiudad, nombreCiudad, esCapital, superficie, seguridad, codigoISOPais)
                VALUES (?, ?, ?, ?, ?, ?);
            """
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, ciudad.codigoCiudad)
                preparedStatement.setString(2, ciudad.nombreCiudad)
                preparedStatement.setBoolean(3, ciudad.esCapital)
                preparedStatement.setDouble(4, ciudad.superficie)
                preparedStatement.setString(5, ciudad.seguridad.toString())
                preparedStatement.setInt(6,ciudad.codigoISOPais)
                preparedStatement.executeUpdate()
            }
        }
        println("Ciudad creada correctamente.")
    }

    fun readAll(): List<Ciudad> {
        val ciudades = mutableListOf<Ciudad>()
        dataSource.connection.use { connection ->
            val selectSQL = "SELECT * FROM ciudades"
            connection.prepareStatement(selectSQL).use { preparedStatement ->
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    ciudades.add(
                        Ciudad(
                            resultSet.getInt("codigoCiudad"),
                            resultSet.getString("nombreCiudad"),
                            resultSet.getBoolean("esCapital"),
                            resultSet.getDouble("superficie"),
                            resultSet.getString("seguridad")[0],
                            resultSet.getInt("codigoISOPais")

                        )
                    )
                }
            }
        }
        return ciudades
    }

    fun update(ciudad: Ciudad) {
        dataSource.connection.use { connection ->
            val updateSQL = """
                UPDATE ciudades
                SET nombreCiudad = ?, esCapital = ?, superficie = ?, seguridad = ?
                WHERE codigoCiudad = ?;
            """
            connection.prepareStatement(updateSQL).use { preparedStatement ->
                preparedStatement.setString(1, ciudad.nombreCiudad)
                preparedStatement.setBoolean(2, ciudad.esCapital)
                preparedStatement.setDouble(3, ciudad.superficie)
                preparedStatement.setString(4, ciudad.seguridad.toString())
                preparedStatement.setInt(5, ciudad.codigoCiudad)
                preparedStatement.executeUpdate()
            }
        }
        println("Ciudad actualizada correctamente.")
    }

    fun delete(codigoCiudad: Int) {
        dataSource.connection.use { connection ->
            val deleteSQL = "DELETE FROM ciudades WHERE codigoCiudad = ?"
            connection.prepareStatement(deleteSQL).use { preparedStatement ->
                preparedStatement.setInt(1, codigoCiudad)
                preparedStatement.executeUpdate()
            }
        }
        println("Ciudad eliminada.")
    }

    fun exportCiudadesToCSV(filename: String) {
        val ciudades = readAll()
        val file = File(filename)
        try {
            FileWriter(file).use { writer ->
                writer.append("CodigoCiudad,NombreCiudad,EsCapital,Superficie,NivelSeguridad,CodigoISOPais\n")
                ciudades.forEach { ciudad ->
                    writer.append("${ciudad.codigoCiudad},${ciudad.nombreCiudad},${ciudad.esCapital},${ciudad.superficie}," +
                            "${ciudad.seguridad},${ciudad.codigoISOPais}\n")
                }
            }
            println("Datos de ciudades exportados correctamente a $filename")
        } catch (e: IOException) {
            println("Error al exportar datos de ciudades a $filename")
            e.printStackTrace()
        }
    }
}

fun requestInt(message: String): Int {
    println(message)
    return readLine()?.toIntOrNull() ?: run {
        println("Por favor, introduce un valor válido.")
        return requestInt(message)
    }
}

fun requestDouble(message: String): Double {
    println(message)
    return readLine()?.toDoubleOrNull() ?: run {
        println("Por favor, introduce un valor válido.")
        return requestDouble(message)
    }
}

fun requestCountryId(verificarPais: VerificarPais): Int {
    val countryId = requestInt("Introduce el codigo ISO del pais:")
    if (!verificarPais.existePais(countryId)) {
        println("No existe un pais con el codigo ISO proporcionado.")
        return requestCountryId(verificarPais)
    }
    return countryId
}

fun main() {
    val verificarPais = VerificarPais()
    val verificarCiudad = verificarCiudad()

    while (true) {
        println("--- Bienvenido a Pais-Ciudad (^-^)/ ---")
        println("Escoge una opción:")
        println("1. Paises")
        println("2. Ciudades")
        println("3. Exportar a archivo CSV")
        println("4. Salir del programa")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                while (true) {
                    println("--- Pais ---")
                    println("Escoge una opción:")
                    println("1. Crear nuevo pais")
                    println("2. Mostrar paises")
                    println("3. Actualizar pais")
                    println("4. Eliminar pais")
                    println("5. Regresar al menú principal")

                    when (readLine()?.toIntOrNull()) {
                        //seleccion opcion 1 para crear pais
                        1 -> {
                            println("Introduce el codigo ISO del pais:")
                            val codigoISO = readLine()?.toIntOrNull() ?: continue
                            println("Introduce el nombre del pais:")
                            val nombrePais = readLine() ?: continue
                            println("Introduce el PIB(nominal) Per cápita de $nombrePais ($):")
                            val pibPais = readLine()?.toDoubleOrNull() ?: continue
                            println("$nombrePais es miembro de la ONU? (true/false):")
                            val miembroONU = readLine()?.toBooleanStrictOrNull() ?: continue
                            println("Introduce el simbolo del dinero del $nombrePais (ejm: $,€,etc):")
                            val simboloDinero = readLine()?.firstOrNull() ?: continue
                            val nuevoPais = Pais(codigoISO, nombrePais, pibPais, miembroONU, simboloDinero)
                            verificarPais.create(nuevoPais)
                        }
                        //seleccion opcion 2 para mostrar paises
                        2 -> {
                            val paises = verificarPais.readAll()
                            if (paises.isEmpty()) {
                                println("No hay paises registrados.")
                            } else {

                                // Encabezados de la tabla
                                println("+--------------+-------------------+--------------------+-------------+----------------+")
                                println("|  Código ISO  |  Nombre del País  | PIB(nom)Per cápita | Miembro ONU | Símbolo Dinero |")
                                println("+--------------+-------------------+--------------------+-------------+----------------+")

                                paises.forEach { pais ->
                                    println(
                                        "| ${pais.codigoISO}          | ${pais.nombrePais.padEnd(17)} | ${
                                            pais.pibPais.toString().padEnd(18)
                                        } | ${pais.miembroONU.toString().padEnd(11)} | ${
                                            pais.simboloDinero.toString().padEnd(14)
                                        } |"
                                    )
                                }

                                println("+--------------+-------------------+--------------------+-------------+----------------+")

                            }
                        }

                        //seleccion opcion 3 para actualizar pais
                        3 -> {
                            println("Introduce el código ISO del país a actualizar:")
                            val codigoISO = readLine()?.toIntOrNull() ?: continue

                            if (!verificarPais.existePais(codigoISO)) {
                                println("No existe un país con el código ISO proporcionado.")
                                continue
                            }

                            val paisExistente = verificarPais.readAll().firstOrNull { it.codigoISO == codigoISO } ?: continue

                            println("Seleccione el campo a actualizar:")
                            println("1. Nombre del país: ${paisExistente.nombrePais}")
                            println("2. PIB del país: ${paisExistente.pibPais}")
                            println("3. Miembro de la ONU: ${paisExistente.miembroONU}")
                            println("4. Símbolo del Dinero: ${paisExistente.simboloDinero}")

                            when (readLine()?.toIntOrNull()) {
                                1 -> {
                                    println("Introduce el nuevo nombre del país:")
                                    val nombrePais = readLine() ?: continue
                                    val paisActualizado = paisExistente.copy(nombrePais = nombrePais)
                                    verificarPais.update(paisActualizado)
                                }
                                2 -> {
                                    println("Introduce el nuevo PIB(nominal) Per cápita del país:")
                                    val pibPais = readLine()?.toDoubleOrNull() ?: continue
                                    val paisActualizado = paisExistente.copy(pibPais = pibPais)
                                    verificarPais.update(paisActualizado)
                                }
                                3 -> {
                                    println("Actualiza si es miembro de la ONU (true/false):")
                                    val miembroONU = readLine()?.toBooleanStrictOrNull() ?: continue
                                    val paisActualizado = paisExistente.copy(miembroONU = miembroONU)
                                    verificarPais.update(paisActualizado)
                                }
                                4 -> {
                                    println("Introduce el nuevo símbolo del dinero del país:")
                                    val simboloDinero = readLine()?.firstOrNull() ?: continue
                                    val paisActualizado = paisExistente.copy(simboloDinero = simboloDinero)
                                    verificarPais.update(paisActualizado)
                                }
                                else -> println("Opción no válida. Por favor, intente de nuevo.")
                            }
                        }

                        //seleccion opcion 4 para eliminar pais
                        4 -> {
                            println("Introduce el codigo ISO del pais a eliminar:")
                            val codigoISO = readLine()?.toIntOrNull() ?: continue

                            if (!verificarPais.existePais(codigoISO)) {
                                println("No existe un pais con el codigo ISO proporcionado.")
                                continue
                            }
                            verificarPais.delete(codigoISO)

                        }
                        5 -> break
                        else -> println("Opción no válida. Por favor, intente de nuevo.")
                    }
                }
            }
            2 -> {
                while (true) {
                    println("--- Ciudad ---")
                    println("Escoge una opción:")
                    println("1. Crear nueva ciudad")
                    println("2. Mostrar ciudades")
                    println("3. Actualizar ciudad")
                    println("4. Eliminar ciudad")
                    println("5. Regresar al menú principal")

                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            println("Introduce el codigo ISO del pais al que pertenece la ciudad: ")
                            val codigoISOPais = readLine()?.toIntOrNull() ?: continue
                            if (!verificarPais.existePais(codigoISOPais)) {
                                println("No existe un pais con el codigo ISO proporcionado.")
                                continue
                            }
                            println("Introduce el codigo de la ciudad:")
                            val codigoCiudad = readLine()?.toIntOrNull() ?: continue
                            println("Introduce el nombre de la ciudad:")
                            val nombreCiudad = readLine()?: continue
                            println("$nombreCiudad es capital? (true/false):")
                            val esCapital = readLine()?.toBooleanStrictOrNull() ?: continue
                            println("Introduce la superficie de la ciudad (en km²):")
                            val superficie = readLine()?.toDoubleOrNull() ?: continue
                            println("Introduce el nivel de seguridad de la ciudad (alto=A, medio=M, bajo=B):")
                            val seguridad = readLine()?.firstOrNull() ?: continue
                            val nuevaCiudad = Ciudad(codigoCiudad, nombreCiudad, esCapital, superficie, seguridad, codigoISOPais)
                            verificarCiudad.create(nuevaCiudad, codigoISOPais)

                        }
                        2 -> {
                            val ciudades = verificarCiudad.readAll()

                            if (ciudades.isEmpty()) {
                                println("No hay ciudades registradas.")
                            } else {
                                println("+--------------+---------------+------------+-----------------+----------------+---------------+")
                                println("| CodigoCiudad | Nombre Ciudad | Es Capital | Superficie(km²) | NivelSeguridad | CodigoISOPais |")
                                println("+--------------+---------------+------------+-----------------+----------------+---------------+")

                                ciudades.forEach { ciudad ->
                                    println("| ${ciudad.codigoCiudad.toString().padEnd(12)} | ${ciudad.nombreCiudad.padEnd(13)} " +
                                            "| ${ciudad.esCapital.toString().padEnd(10)} | ${ciudad.superficie.toString().padEnd(15)}" +
                                            " | ${ciudad.seguridad.toString().padEnd(14)} | ${ciudad.codigoISOPais.toString().padEnd(13)} |")
                                }
                                println("+--------------+---------------+------------+-----------------+----------------+---------------+")
                            }
                        }


                        //seleccion opcion 3 para actualizar ciudad

                        3 -> {

                            println("Introduce el codigo ISO del pais al que pertenece la ciudad: ")
                            val codigoISOPais = readLine()?.toIntOrNull() ?: continue
                            if (!verificarPais.existePais(codigoISOPais)) {
                                println("No existe un pais con el codigo ISO proporcionado.")
                                continue
                            }
                            println("Introduce el codigo de la ciudad a actualizar:")
                            val codigoCiudad = readLine()?.toIntOrNull() ?: continue
                            if (!verificarCiudad.existeCiudad(codigoCiudad)) {
                                println("No existe una ciudad con el codigo proporcionado.")
                                continue
                            }
                            val ciudadExistente=verificarCiudad.readAll().firstOrNull{ it.codigoCiudad==codigoCiudad}?:continue

                            println("Selecciona el campo a actualizar:")
                            println("1. Nombre de la ciudad")
                            println("2. Es capital")
                            println("3. Superficie")
                            println("4. Seguridad")

                            when (readLine()?.toIntOrNull()) {
                                1 -> {
                                    println("Introduce el nuevo nombre de la ciudad:")
                                    val nombreCiudad = readLine()?: continue
                                    val ciudadActualizada = ciudadExistente.copy(nombreCiudad = nombreCiudad)
                                    verificarCiudad.update(ciudadActualizada)
                                }
                                2 -> {
                                    println("Actualiza si la ciudad es capital (true/false):")
                                    val esCapital = readLine()?.toBooleanStrictOrNull()?: continue
                                    val ciudadActualizada = ciudadExistente.copy(esCapital = esCapital)
                                    verificarCiudad.update(ciudadActualizada)
                                }
                                3 -> {
                                    println("Introduce la nueva superficie de la ciudad (km²):")
                                    val superficie = readLine()?.toDoubleOrNull()?: continue
                                    val ciudadActualizada = ciudadExistente.copy(superficie = superficie)
                                    verificarCiudad.update(ciudadActualizada)
                                }
                                4 -> {
                                    println("Introduce el nuevo nivel de seguridad de la ciudad:")
                                    val seguridad= readLine()?.firstOrNull() ?: continue
                                    val ciudadActualizada = ciudadExistente.copy(seguridad = seguridad)
                                    verificarCiudad.update(ciudadActualizada)
                                }
                                else -> {
                                    println("Opción no válida. Por favor, intente de nuevo.")
                                }

                            }



                        }

                        //seleccion opcion 4 para eliminar ciudad
                        4 -> {
                            println("Introduce el codigo de la ciudad a eliminar:")
                            val codigoCiudad = readLine()?.toIntOrNull() ?: continue
                            if (!verificarCiudad.existeCiudad(codigoCiudad)) {
                                println("No existe una ciudad con el codigo proporcionado.")
                                continue
                            }
                            verificarCiudad.delete(codigoCiudad)

                        }
                        5 -> break
                        else -> println("Opción no válida. Por favor, intente de nuevo.")
                    }
                }
            }

            3 -> {
                while (true){
                    println("1. Exportar paises a archivo CSV")
                    println("2. Exportar ciudades a archivo CSV")
                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            // Exportar países a CSV
                            println("Introduce el nombre del archivo para exportar países (sin extensión):")
                            val filenamePaises = readLine()
                            verificarPais.exportPaisesToCSV("$filenamePaises.csv")
                        }
                        2 -> {
                            // Exportar ciudades a CSV
                            println("Introduce el nombre del archivo para exportar ciudades (sin extensión):")
                            val filenameCiudades = readLine()
                            verificarCiudad.exportCiudadesToCSV("$filenameCiudades.csv")
                        }
                        3 -> break
                        else -> println("Opción no válida. Por favor, intente de nuevo.")

                    }

                }

            }

            4 -> {
                println("Saliendo del programa.")
                return
            }
            else -> println("Opción no válida. Por favor, intente de nuevo.")
        }
        println()
    }
}
