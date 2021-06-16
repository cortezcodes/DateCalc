package com.example.datecalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.datecalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //set ArrayAdapter for month spinner
        val fromMonthSpinner = binding.spinnerFromMonth
        ArrayAdapter.createFromResource(this,R.array.months_array,
            R.layout.spinner_item).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            fromMonthSpinner.adapter = arrayAdapter
        }

        //set ArrayAdapter for from year spinner

        val view = binding.root
        setContentView(view)
    }


}