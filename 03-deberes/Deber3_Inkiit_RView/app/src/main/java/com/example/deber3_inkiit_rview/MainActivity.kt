package com.example.deber3_inkiit_rview

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var recommendationRecyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var bookList: ArrayList<book>
    private lateinit var recommendationList: ArrayList<recommendation>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        // Inicializar la lista de libros
        bookList = ArrayList<book>().apply {
            add(book("Relatos Paranormales", "Relatos Paranormales", "35 relatos paranormales inspirados en la vida real, que narran distintas experiencias sobrenaturales que van desde encuentros con espíritus, hasta pactos de sangre.", "Completa", 4.5, R.drawable.ik1))
            add(book("Secretos ocultos", "Jimena Paez", "Dos hermanas idénticas fueron abandonadas después de nacer creciendo en un orfanato. Ambas son muy parecidas físicamente pero muy diferentes en su interior. ", "Completa", 5.0, R.drawable.ik2))
            add(book("La Leyenda de Licaón", " TLillo", "Samuel es un niño de cinco años al que la vida no ha tratado bien. Su madre, lo único que tenía en el mundo, se ha ido frente a sus ojos de una forma violenta.", "Completa", 4.2, R.drawable.ik3))
            add(book("No te vayas", " Nakary", "El amor verdadero es el sentimiento de afecto, pasión, intimidad y compromiso genuino que una persona siente por otra. En mi opinión eso es falso.. Encontrar el amor es un agradable accidente. ", "En curso", 4.2, R.drawable.ik4))
            add(book("Creepypastas","Im Ingrid", "Adéntrate en las profundidades perturbadoras de la mente humana con este libro inspirado en los relatos de terror conocidos como 'Creepypastas'.", "En proceso",4.8,R.drawable.ik5))
            add(book("Algo Ocultan","Neymichelle26","En un pueblo llamado \"Los santos\" se encuentran cuatro chicos misteriosos cada uno tiene un don.Todos en ese pueblo les temen menos Sarah Wenham ella sabe que Algo ocultan y quiere descubrirlo.","",4.9,R.drawable.ik6))
            add(book("Intersección ","Luna","¿Qué estás dispuesto a hacer para proteger a los tuyos? ¿Estarías dispuesto a llenar tus manos de sangre? ¿Dejarías la moral de lo que está «bien»? ","",4.1,R.drawable.ik7))
            add(book("EL PILOTO DE LA NACIÓN DEL OESTE","Karol Erskin","Era un niño cuando su padre murió, hoy tiene que competir con otros jóvenes para ser seleccionado el piloto de su nación. En la base militar reconocerá lo absoluto del miedo, del odio, y del amor. ","Completa",5.0,R.drawable.ik8))
            add(book("HASTA EL INFINITO","DotzeTaronjes","Adit es un chico que siempre soñó con ser un pirata. Un día, su tío le dio la oportunidad de salir al espacio exterior con un barco de piratas para emprender su primera aventura. ","En curso",4.5,R.drawable.ik9))
            add(book("Dos en Próxima","Benjamín Cochia","Destino o determinación, ¿cuál dicta el resultado? Adam debe decir adiós al mundo tal y como lo conocía. Su activa vida social y su espíritu desenfadado se ven interrumpidos por la llegada de su desconocido hermano gemelo","En curso",4.2,R.drawable.ik10))

            // Agrega más libros según sea necesario
        }

        // Configurar el RecyclerView para libros
        bookRecyclerView = findViewById(R.id.recyclerViewBooks)
        bookAdapter = BookAdapter(bookList)
        bookRecyclerView.layoutManager = LinearLayoutManager(this)
        bookRecyclerView.adapter = bookAdapter

        // Inicializar la lista de recomendaciones
        recommendationList = ArrayList<recommendation>().apply {
            add(recommendation(R.drawable.ik7, "Intersección", "¿Qué estás dispuesto a hacer para proteger a los tuyos? ¿Estarías dispuesto a llenar tus manos de sangre? ¿Dejarías la moral de lo que está «bien»?"))
            add(recommendation(R.drawable.ik9, "HASTA EL INFINITO", "Adit es un chico que siempre soñó con ser un pirata. Un día, su tío le dio la oportunidad de salir al espacio exterior con un barco de piratas para emprender su primera aventura."))
            add(recommendation(R.drawable.ik5, "Creepypastas", "Adéntrate en las profundidades perturbadoras de la mente humana con este libro inspirado en los relatos de terror conocidos como 'Creepypastas'."))
            // Agrega más recomendaciones según sea necesario
        }

        // Configurar el RecyclerView para recomendaciones
        recommendationRecyclerView = findViewById(R.id.recyclerViewRecommendations)
        recommendationAdapter = RecommendationAdapter(recommendationList)
        recommendationRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendationRecyclerView.adapter = recommendationAdapter

        // Iniciar el desplazamiento automático para las recomendaciones
        startAutoScrollForRecommendations()

        // Obtener el botón de opciones
        val btnOptions = findViewById<ImageButton>(R.id.btnOptions)

        // Configurar el clic del botón de opciones
        btnOptions.setOnClickListener {
            // Crear un menú emergente (popup menu)
            val popupMenu = PopupMenu(this, btnOptions, R.color.white)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.menu_options, popupMenu.menu)

            // Manejar clics en elementos del menú
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_option1 -> {
                        // Lógica para la opción 1
                        true
                    }

                    R.id.action_option2 -> {
                        // Lógica para la opción 2
                        true
                    }

                    else -> false
                }
            }

            // Mostrar el menú emergente
            popupMenu.show()
        }

    }
    private fun startAutoScrollForRecommendations() {
        val handler = android.os.Handler(mainLooper)
        val autoScrollRunnable = object : Runnable {
            override fun run() {
                val visibleItemCount = (recommendationRecyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (recommendationRecyclerView.layoutManager as LinearLayoutManager).itemCount
                val firstVisibleItemPosition = (recommendationRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    // Si llegamos al final, reinicia al principio
                    recommendationRecyclerView.smoothScrollToPosition(0)
                } else {
                    // Desplázate al siguiente elemento
                    recommendationRecyclerView.smoothScrollToPosition(firstVisibleItemPosition + 1)
                }

                // Programar el siguiente desplazamiento automático después de un intervalo de tiempo
                handler.postDelayed(this, AUTO_SCROLL_DELAY)
            }
        }

        // Iniciar el desplazamiento automático
        handler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY)
    }

    companion object {
        const val AUTO_SCROLL_DELAY = 3000L // 3000 milisegundos (3 segundos) entre cada desplazamiento automático
    }
}
