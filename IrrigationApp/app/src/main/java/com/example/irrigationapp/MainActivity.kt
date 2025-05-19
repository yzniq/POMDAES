package com.example.irrigationapp

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.widget.TextView
class MainActivity : AppCompatActivity() {

    private val viewModel: IrrigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moistureText = findViewById<TextView>(R.id.moistureText)
        val thresholdInput = findViewById<EditText>(R.id.thresholdInput)
        val setButton = findViewById<Button>(R.id.setButton)
        val refreshButton = findViewById<Button>(R.id.refreshButton)
        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val startButton = findViewById<Button>(R.id.startButton)
        val humidityTextView = findViewById<TextView>(R.id.humidityTextView)


        viewModel.moisture.observe(this, Observer {
            moistureText.text = "Влажность: $it%"
        })
        viewModel.wateringStatus.observe(this) { isOn ->
            statusTextView.text = if (isOn) "Полив включен" else "Полив выключен"
            startButton.text = if (isOn) "Остановить полив" else "Старт полива"
        }
        viewModel.moisture.observe(this) {
            moistureText.text = "Порог влажности: $it%"
            humidityTextView.text = "Текущая влажность: $it%"
        }
        setButton.setOnClickListener {
            val newThreshold = thresholdInput.text.toString()
            viewModel.setThreshold(newThreshold)
        }

        refreshButton.setOnClickListener {
            viewModel.refreshMoisture()
        }
        startButton.setOnClickListener {
            viewModel.toggleWatering()
        }

        viewModel.refreshMoisture()
    }
}