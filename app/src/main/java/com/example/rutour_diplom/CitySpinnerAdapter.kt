package com.example.rutour_diplom

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CitySpinnerAdapter(context: Context, private val cities: List<Models.City>) : ArrayAdapter<Models.City>(context, android.R.layout.simple_spinner_item, cities) {
    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getItem(position: Int): Models.City? {
        return cities[position]
    }

    override fun getCount(): Int {
        return cities.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).text = getItem(position)?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view as TextView).text = getItem(position)?.name
        return view
    }
}