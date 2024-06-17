package com.example.rutour_diplom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HotelAdapter(private val hotels: List<Models.Hotel>) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    class HotelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hotelName: TextView = view.findViewById(R.id.hotelName)
        val hotelRating: RatingBar = view.findViewById(R.id.hotelRating)
        val hotelImage: ImageView = view.findViewById(R.id.hotelImage)
        val hotelPrice: TextView = view.findViewById(R.id.hotelPrice)
        val hotelDescription: TextView = view.findViewById(R.id.hotelDescription) // Добавлено
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_hotel, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.hotelName.text = hotel.name
        holder.hotelRating.rating = hotel.rating
        holder.hotelPrice.text = "Цена: ${hotel.price} руб."
        holder.hotelDescription.text = hotel.description // Добавлено
        Glide.with(holder.hotelImage.context).load(hotel.imageUrl).into(holder.hotelImage)
    }

    override fun getItemCount(): Int {
        return hotels.size
    }
}
