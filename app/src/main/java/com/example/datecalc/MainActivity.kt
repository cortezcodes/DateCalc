package com.example.datecalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.datecalc.databinding.ActivityMainBinding
import java.time.DateTimeException
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fromMonth = 0
    private var fromDay = 0
    private var toMonth = 0
    private var toDay = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val fromMonthSpinner = binding.spinnerFromMonth
        val toMonthSpinner = binding.spinnerToMonth
        val fromDaySpinner = binding.spinnerFromDay
        val toDaySpinner = binding.spinnerToDay
        val fromYearEditText = binding.etFromYear
        val toYearEditText = binding.etToYear
        val calculateButton = binding.btnCalculate
        val outputTextview = binding.tvOutput
        val fromCalendarImgBtn = binding.imgBtnFromCalendar
        val toCalendarImgBtn = binding.imgBtnToCalendar

        //initial setup of day spinners
        fromDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array)
        toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array)


        //set ArrayAdapter for month spinner
        ArrayAdapter.createFromResource(this,R.array.months_array,
            R.layout.spinner_item).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            fromMonthSpinner.adapter = arrayAdapter
        }

        fromMonthSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    when(pos){ //based off the month select change the number of days within the month
                        4,6,9,11 -> {
                            fromDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array_short)
                            fromDaySpinner.setSelection(fromDay)
                        }
                        1,3,5,7,8,10,12 -> {
                            fromDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array)
                            fromDaySpinner.setSelection(fromDay)
                        }
                        2 -> {
                            fromDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array_feb)
                            fromDaySpinner.setSelection(fromDay)
                        }
                    }
                fromMonth = pos
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //set ArrayAdapter for to month spinner
        ArrayAdapter.createFromResource(this,R.array.months_array,
            R.layout.spinner_item).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            toMonthSpinner.adapter = arrayAdapter
        }

        toMonthSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when(pos){
                    4,6,9,11 -> toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array_short)
                    1,3,5,7,8,10,12 -> toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array)
                    2 -> toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array_feb)
                }
                toMonth = pos
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        fromDaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                fromDay = pos
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        toDaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                toDay = pos
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }



        //set onClickListener for calculate button
        calculateButton.setOnClickListener {
            var fromYear = fromYearEditText.text
            var toYear = toYearEditText.text

            if(!TextUtils.isEmpty(fromYear) && !TextUtils.isEmpty(toYear)) {
                try {
                    var fromEvent = LocalDate.of(fromYear.toString().toInt(), fromMonth, fromDay)
                    var toEvent = LocalDate.of(toYear.toString().toInt(), toMonth, toDay)
                    val string = Event.fullResponse(fromEvent, toEvent)
                    outputTextview.text = string
                } catch (e:DateTimeException){
                    val string = "Invalid input, please check the following: \n" +
                            "1. Feb 29 must fall on a leap year.\n" +
                            "2. All fields must have valid input."
                    Log.e("DateTimeException", e.toString())
                    outputTextview.text  = string
                }

            } else{
                val str = "Please include years"
                outputTextview.text = str
                //TODO("Make the empty year editText highlighted")
            }
        }

        val view = binding.root

        //set onclicklistener for DatePicker
        fromCalendarImgBtn.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{
                    view, year, monthofYear, dayOfMonth -> {}
                    fromYearEditText.setText(year.toString())
                    fromMonthSpinner.setSelection(monthofYear+1)
                    fromDaySpinner.setSelection(dayOfMonth)
                    fromDay = dayOfMonth
                }, year, month, day)
            datePickerDialog.show()
        }
        setContentView(view)
    }


    fun setDaySpinnerAdapter(res: Int ): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(this, res, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        return adapter
    }
}