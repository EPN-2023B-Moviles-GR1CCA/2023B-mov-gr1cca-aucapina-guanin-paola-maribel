package gui
import javax.swing.*

class MainGUI : JFrame("CRUD App-Móviles 2023B") {
    // Aquí debes agregar los componentes necesarios (botones, campos de texto, etc.)
    // y configurar el diseño de la interfaz utilizando Swing.
    // Puedes usar el GUI Designer de IntelliJ para esto.

    init {
        // Configuración de la ventana principal

        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(400,300)
        // Agrega los componentes y diseña la interfaz aquí


    }
}
fun main() {
    SwingUtilities.invokeLater {
        MainGUI().isVisible = true
    }
}