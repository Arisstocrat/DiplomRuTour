package com.example.rutour_diplom

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExcursionActivity : AppCompatActivity() {

    private lateinit var spinnerCities: Spinner
    private lateinit var spinnerSort: Spinner
    private lateinit var editTextPrice: EditText
    private lateinit var recyclerViewExcursions: RecyclerView
    private lateinit var excursionAdapter: ExcursionAdapter
    private var selectedCity: String = ""
    private var selectedSortOption: String = "Rating Descending"
    private var maxPrice: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excursion)

        spinnerCities = findViewById(R.id.spinnerCities)
        spinnerSort = findViewById(R.id.spinnerSort)
        editTextPrice = findViewById(R.id.editTextPrice)
        recyclerViewExcursions = findViewById(R.id.recyclerViewHotels)

        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Models.DataProvider.cities.map { it.name })
        spinnerCities.adapter = cityAdapter

        val sortOptions = listOf("Рейтинг по убыванию", "Рейтинг по возрастанию", "Цена по возрастанию", "Цена по убыванию")
        val sortAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortOptions)
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSort.adapter = sortAdapter

        recyclerViewExcursions.layoutManager = LinearLayoutManager(this)
        excursionAdapter = ExcursionAdapter(emptyList())
        recyclerViewExcursions.adapter = excursionAdapter

        spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCity = parent.getItemAtPosition(position) as String
                updateExcursionList()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedSortOption = parent.getItemAtPosition(position) as String
                updateExcursionList()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        editTextPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                maxPrice = if (s.isNullOrEmpty()) null else s.toString().toFloat()
                updateExcursionList()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateExcursionList() {
        var excursions = Models.DataProvider.excursions[selectedCity] ?: emptyList()
        if (maxPrice != null) {
            excursions = excursions.filter { it.price <= maxPrice!! }
        }
        excursions = when (selectedSortOption) {
            "Рейтинг по возрастанию" -> excursions.sortedBy { it.rating }
            "Рейтинг по убыванию" -> excursions.sortedByDescending { it.rating }
            "Цена по возрастанию" -> excursions.sortedBy { it.price }
            "Цена по убыванию" -> excursions.sortedByDescending { it.price }
            else -> excursions
        }
        excursionAdapter = ExcursionAdapter(excursions)
        recyclerViewExcursions.adapter = excursionAdapter
    }
    fun onClick3(view: View) {
        val intent = Intent(this, email::class.java)
        startActivity(intent)
    }
}
