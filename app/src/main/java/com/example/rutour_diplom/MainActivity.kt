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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rutour_diplom.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCities: Spinner
    private lateinit var spinnerSort: Spinner
    private lateinit var editTextPrice: EditText
    private lateinit var recyclerViewHotels: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter
    private var selectedCity: String = ""
    private var selectedSortOption: String = "Rating Descending"
    private var maxPrice: Float? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Проверка наличия авторизованного пользователя
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish() // Завершение MainActivity, чтобы предотвратить возвращение пользователя
        }

        binding.logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // Выход из учетной записи
            startActivity(Intent(this, LoginActivity::class.java)) // Переход обратно на экран логина
            finish() // Завершение MainActivity после выхода
        }

        spinnerCities = binding.spinnerCities
        spinnerSort = binding.spinnerSort
        editTextPrice = binding.editTextPrice
        recyclerViewHotels = binding.recyclerViewHotels

        val cityAdapter = CitySpinnerAdapter(this, Models.DataProvider.cities)
        spinnerCities.adapter = cityAdapter

        val sortOptions = listOf("Худший Рейтинг", "Лучший Рейтинг", "Наименьшая цена", "Наибольшая цена")
        val sortAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortOptions)
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSort.adapter = sortAdapter

        recyclerViewHotels.layoutManager = LinearLayoutManager(this)
        hotelAdapter = HotelAdapter(emptyList())
        recyclerViewHotels.adapter = hotelAdapter

        spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCity = (parent.getItemAtPosition(position) as Models.City).name
                updateHotelList()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedSortOption = parent.getItemAtPosition(position) as String
                updateHotelList()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        editTextPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                maxPrice = if (s.isNullOrEmpty()) null else s.toString().toFloat()
                updateHotelList()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateHotelList() {
        var hotels = Models.DataProvider.hotels[selectedCity] ?: emptyList()
        if (maxPrice != null) {
            hotels = hotels.filter { it.price <= maxPrice!! }
        }
        hotels = when (selectedSortOption) {
            "Худший Рейтинг" -> hotels.sortedBy { it.rating }
            "Лучший Рейтинг" -> hotels.sortedByDescending { it.rating }
            "Наименьшая цена" -> hotels.sortedBy { it.price }
            "Наибольшая цена" -> hotels.sortedByDescending { it.price }
            else -> hotels
        }
        hotelAdapter = HotelAdapter(hotels)
        recyclerViewHotels.adapter = hotelAdapter
    }

    fun onClickex(view: View) {
        val intent = Intent(this, ExcursionActivity::class.java)
        startActivity(intent)
    }

    fun onClick3(view: View) {
        val intent = Intent(this, email::class.java)
        startActivity(intent)
    }
}
