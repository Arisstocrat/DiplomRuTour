package com.example.rutour_diplom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ExcursionAdapter(private val excursions: List<Models.Excursion>) : RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder>() {

    class ExcursionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val excursionName: TextView = view.findViewById(R.id.excursionName)
        val excursionRating: RatingBar = view.findViewById(R.id.excursionRating)
        val excursionImage: ImageView = view.findViewById(R.id.excursionImage)
        val excursionPrice: TextView = view.findViewById(R.id.excursionPrice)
        val excursionDescription: TextView = view.findViewById(R.id.excursionDescription) // Добавлено
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExcursionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_excursion, parent, false)
        return ExcursionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExcursionViewHolder, position: Int) {
        val excursion = excursions[position]
        holder.excursionName.text = excursion.name
        holder.excursionRating.rating = excursion.rating
        holder.excursionPrice.text = "Цена: ${excursion.price} руб."
        holder.excursionDescription.text = excursion.description // Добавлено
        Glide.with(holder.excursionImage.context).load(excursion.imageUrl).into(holder.excursionImage)
    }

    override fun getItemCount(): Int {
        return excursions.size
    }
}
