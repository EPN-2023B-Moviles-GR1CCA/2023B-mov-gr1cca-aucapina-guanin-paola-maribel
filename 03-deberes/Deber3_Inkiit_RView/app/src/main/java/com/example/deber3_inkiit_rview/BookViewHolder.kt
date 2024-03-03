package com.example.deber3_inkiit_rview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(book: book) {
        itemView.findViewById<TextView>(R.id.textViewTitulo).text = book.titulo
        itemView.findViewById<TextView>(R.id.textViewAutor).text = book.autor
        itemView.findViewById<TextView>(R.id.textViewDescripcion).text = book.descripcion
        itemView.findViewById<TextView>(R.id.textViewValor).text = book.calificacion.toString()
        itemView.findViewById<TextView>(R.id.textViewEstado).text = book.estado

        // Cargar la imagen desde res/drawable
        val coverImageView = itemView.findViewById<ImageView>(R.id.imageViewCoverBook)
        // Utiliza setImageResource para establecer la imagen desde el recurso drawable
        coverImageView.setImageResource(book.coverBook)
    }
}