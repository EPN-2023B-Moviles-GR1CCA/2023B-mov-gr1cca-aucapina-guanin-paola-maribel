package com.example.deber3_inkiit_rview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(recommendation: recommendation) {
        val coverImageView = itemView.findViewById<ImageView>(R.id.imageViewCoverR)
        val tituloTextView = itemView.findViewById<TextView>(R.id.textViewTituloR)
        val descripcionTextView = itemView.findViewById<TextView>(R.id.textViewDescripcionR)

        coverImageView.setImageResource(recommendation.coverR)
        tituloTextView.text = recommendation.tituloR
        descripcionTextView.text = recommendation.descripcionR
    }
}
