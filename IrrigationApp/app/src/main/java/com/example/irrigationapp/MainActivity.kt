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


        viewModel.moisture.observe(this, Observer {
            moistureText.text = "Влажность: $it%"
        })

        setButton.setOnClickListener {
            val newThreshold = thresholdInput.text.toString()
            viewModel.setThreshold(newThreshold)
        }

        refreshButton.setOnClickListener {
            viewModel.refreshMoisture()
        }

        viewModel.refreshMoisture()
    }
}