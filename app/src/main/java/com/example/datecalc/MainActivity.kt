package com.example.datecalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.datecalc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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

        //set ArrayAdapter for to month spinner

        val toMonthSpinner = binding.spinnerToMonth
        ArrayAdapter.createFromResource(this,R.array.months_array,
            R.layout.spinner_item).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            toMonthSpinner.adapter = arrayAdapter
        }

        //set ArrayAdapter for from day spinner
        val fromDaySpinner = binding.spinnerFromDay
        ArrayAdapter.createFromResource(this,R.array.day_array,R.layout.spinner_item).also{
                arrayAdapter -> arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            fromDaySpinner.adapter = arrayAdapter
        }

        //set ArrayAdapter for to day spinner
        val toDaySpinner = binding.spinnerToDay
        ArrayAdapter.createFromResource(this,R.array.day_array,R.layout.spinner_item).also{
                arrayAdapter -> arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            toDaySpinner.adapter = arrayAdapter
        }

        val view = binding.root
        setContentView(view)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            when(parent?.id){
                binding.spinnerFromMonth.id -> {

                }
            }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}