package com.example.deber3_inkiit_rview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
            add(book("Relatos Paranormales ", "Autor 1", "35 relatos paranormales inspirados en la vida real, que narran distintas experiencias sobrenaturales que van desde encuentros con espíritus, hasta pactos de sangre.", "Completa", 4.5, R.drawable.ik1))
            add(book("Título 2", "Autor 2", "Descripción 2", "Estado 2", 3.8, R.drawable.ik1))
            add(book("Título 3", "Autor 3", "Descripción 3", "Estado 3", 4.2, R.drawable.ik1))
            // Agrega más libros según sea necesario
        }

        // Configurar el RecyclerView para libros
        bookRecyclerView = findViewById(R.id.recyclerViewBooks)
        bookAdapter = BookAdapter(bookList)
        bookRecyclerView.layoutManager = LinearLayoutManager(this)
        bookRecyclerView.adapter = bookAdapter

        // Inicializar la lista de recomendaciones
        recommendationList = ArrayList<recommendation>().apply {
            add(recommendation(R.drawable.ik1, "Recomendación 1", "Descripción 1"))
            add(recommendation(R.drawable.ik1, "Recomendación 2", "Descripción 2"))
            add(recommendation(R.drawable.ik1, "Recomendación 3", "Descripción 3"))
            // Agrega más recomendaciones según sea necesario
        }

        // Configurar el RecyclerView para recomendaciones
        recommendationRecyclerView = findViewById(R.id.recyclerViewRecommendations)
        recommendationAdapter = RecommendationAdapter(recommendationList)
        recommendationRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendationRecyclerView.adapter = recommendationAdapter

        // Iniciar el desplazamiento automático para las recomendaciones
        startAutoScrollForRecommendations()
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
