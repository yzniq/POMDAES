package com.example.irrigationapp

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IrrigationViewModel : ViewModel() {


    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.100:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(IrrigationApi::class.java)

    val moisture = MutableLiveData<String>("Загрузка...")

    fun refreshMoisture() {
        viewModelScope.launch {
            try {
                val response = api.getMoisture()
                if (response.isSuccessful) {
                    val html = response.body().orEmpty()
                    val regex = Regex("""Влажность: (\d+(?:\.\d+)?)%""")
                    val match = regex.find(html)
                    val value = match?.groupValues?.get(1) ?: "?"
                    moisture.value = value
                } else {
                    moisture.value = "Ошибка"
                }
            } catch (e: Exception) {
                moisture.value = "Ошибка"
            }
        }
    }

    fun setThreshold(value: String) {
        viewModelScope.launch {
            try {
                api.setThreshold(value)
                refreshMoisture()
            } catch (_: Exception) {}
        }
    }
}