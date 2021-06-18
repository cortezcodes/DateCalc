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
import java.lang.NumberFormatException
import java.time.DateTimeException
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fromMonth = 0
    private var fromDay = 0
    private var toMonth = 0
    private var toDay = 0
    private lateinit var outputTextview: TextView
    private val ISVALIDINPUT_FALSE = 0
    private val ISVALIDINPUT_TRUE = 1
    private val ISVALIDINPUT_FROM_DEFAULT = 2
    private val ISVALIDINPUT_TO_DEFAULT = 3
    private val ISVALIDINPUT_ALL_DEFAULT = 4




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
        outputTextview = binding.tvOutput
        val fromCalendarImgBtn = binding.imgBtnFromCalendar
        val toCalendarImgBtn = binding.imgBtnToCalendar
        val toADBCSwitch = binding.switchToBcAd
        val fromADBCSwitch = binding.switchFromBcAd

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
                    4,6,9,11 -> {
                        toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array_short)
                        toDaySpinner.setSelection(toDay)
                    }
                    1,3,5,7,8,10,12 -> {
                        toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array)
                        toDaySpinner.setSelection(toDay)
                    }
                    2 -> {
                        toDaySpinner.adapter = setDaySpinnerAdapter(R.array.day_array_feb)
                        toDaySpinner.setSelection(toDay)
                    }
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

            }

        }

        toDaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                toDay = pos
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }



        //set onClickListener for calculate button
        calculateButton.setOnClickListener {
            var fromYear = 0
            var toYear = 0
            try{ // catch exception if EditText is empty
                fromYear = fromYearEditText.text.toString().toInt()
            } catch (e: NumberFormatException){
                outputTextview.text = getString(R.string.from_year_exception)
                Log.e("Retrieving fromYearEditText", e.toString())
                return@setOnClickListener
            }
            try { // catch exception if EditText is empty
                toYear = toYearEditText.text.toString().toInt()
            }catch (e: NumberFormatException){
                outputTextview.text = getString(R.string.to_year_exception)
                Log.e("Retrieving toYearEditText", e.toString())
                return@setOnClickListener
            }

            if(fromADBCSwitch.isActivated){ //check if AD BC switch is active
                fromYear *= -1
                Log.d("fromADBCSwitch","Switch is activated to B.C., year must" +
                        " be a negative value. fromYear = ${fromYear}")
            }
            if(toADBCSwitch.isActivated){  //check if AD BC switch is active
                toYear *= -1
                Log.d("toADBCSwitch","Switch is activated to B.C., year must" +
                        " be a negative value. toYear = ${toYear}")
            }
            when(isValidInput()){
                ISVALIDINPUT_FALSE -> return@setOnClickListener
                ISVALIDINPUT_TRUE -> outputAnswer(fromYear, fromMonth, fromDay, toYear, toMonth, toDay)
                ISVALIDINPUT_FROM_DEFAULT -> outputAnswer(fromYear, 1, 1, toYear, toMonth, toDay)
                ISVALIDINPUT_TO_DEFAULT -> outputAnswer(fromYear, fromMonth, fromDay, toYear, 1, 1)
                ISVALIDINPUT_ALL_DEFAULT->outputAnswer(fromYear,1,1,toYear, 1,1)
            }


        }

        val view = binding.root

        //set onclicklistener for fromDatePicker
        fromCalendarImgBtn.setOnClickListener{
            val calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            //if the fields have already been set, then set the calendar to that date
            if(fromMonth != 0) month = fromMonth - 1 //DatePickers months are 0 = January & 11 = December
            if(fromDay != 0) day = fromDay
            if(fromYearEditText.text.toString() != ""){ // year is only between 1900 and 2100
                val yearLimiter = fromYearEditText.text.toString().toInt()
                if(yearLimiter <= 2100 && yearLimiter >= 1900) year = fromYearEditText.text.toString().toInt()
                else if(yearLimiter >2100) year = 2100
                else year = 1900
            }


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

        //set onclickListener for toDateCalendarBtn
        toCalendarImgBtn.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{
                        view, year, monthofYear, dayOfMonth -> {}
                    toYearEditText.setText(year.toString())
                    toMonthSpinner.setSelection(monthofYear+1)
                    toDaySpinner.setSelection(dayOfMonth)
                    toDay = dayOfMonth
                }, year, month, day)
            datePickerDialog.show()
        }
        setContentView(view)
    }

    fun outputAnswer(fYear: Int, fMonth: Int, fDay: Int, tYear: Int, tMonth: Int, tDay: Int){
        try{
            var fromEvent = LocalDate.of(fYear, fMonth, fDay)
            var toEvent = LocalDate.of(tYear, tMonth, tDay)
            val string = Event.fullResponse(fromEvent, toEvent)
            outputTextview.text = string
        } catch (e:DateTimeException){
            val string = "Invalid input, please check the following: \n" +
                    "1. Feb 29 must fall on a leap year.\n" +
                    "2. All fields must have valid input."
            Log.e("DateTimeException", e.toString())
            outputTextview.text  = string
        }
    }

    fun isValidInput():Int{

        //Ensure all valid fields have a valid input
        Log.d("isValidInput", " fromMonth = ${fromMonth} fromDay = ${fromDay}" +
                "\ntoMonth = ${toMonth} toDay = ${toDay}")
        if(fromMonth == 0 && fromDay == 0 && toMonth == 0 && toDay == 0){
            Log.d("isValidInput", "returned ISVALIDINPUT_ALL_DEFAULT")
            return ISVALIDINPUT_ALL_DEFAULT
        }
        if(fromMonth == 0 && fromDay != 0) {
            outputTextview.text = getString(R.string.from_month_missing)
            Log.d("isValidInput", "returned ISVALIDINPUT_FALSE for missing from month")
            return ISVALIDINPUT_FALSE
        } else if(fromDay == 0 && fromMonth != 0){
            outputTextview.text = getString(R.string.from_day_missing)
            Log.d("isValidInput", "returned ISVALIDINPUT_FALSE for missing from day")
            return ISVALIDINPUT_FALSE
        } else if(fromMonth == 0 && fromDay == 0){
            // Only from year has been input, then Month & Day are defaulted to Jan 1st
            Log.d("isValidInput", "returned ISVALIDINPUT_FROM_DEFAULT")
            return ISVALIDINPUT_FROM_DEFAULT
        }

        //Ensure all valid fields have a valid input
        if(toMonth == 0 && toDay != 0) {
            outputTextview.text = getString(R.string.to_month_missing)
            Log.d("isValidInput", "returned ISVALIDINPUT_FALSE for missing to month")
            return ISVALIDINPUT_FALSE
        } else if(toDay == 0 && toMonth != 0){
            outputTextview.text = getString(R.string.to_day_missing)
            Log.d("isValidInput", "returned ISVALIDINPUT_FALSE for missing to day")
            return ISVALIDINPUT_FALSE
        } else if(toMonth == 0 && toDay == 0){
            // Only from year has been input, then Month & Day are defaulted to Jan 1st.
            Log.d("isValidInput", "returned ISVALIDINPUT_TO_DEFAULT")
            return ISVALIDINPUT_TO_DEFAULT
        }
        return ISVALIDINPUT_TRUE
    }


    fun setDaySpinnerAdapter(res: Int ): ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(this, res, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        return adapter
    }
}