package com.example.datecalc

import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.math.abs


class Event (){
    companion object {
        @JvmStatic
        fun yearsBetween(date: LocalDate, date2: LocalDate):String{
            val i = ChronoUnit.YEARS.between(date, date2)
            val formatted = NumberFormat.getIntegerInstance().format(i)
            return "${formatted} total years"
        }

        @JvmStatic
        fun monthsBetween(date: LocalDate, date2: LocalDate):String{
            val i = ChronoUnit.MONTHS.between(date, date2)
            val formatted = NumberFormat.getIntegerInstance().format(i)
            return "${formatted} total months"
        }

        @JvmStatic
        fun daysBetween(date: LocalDate, date2: LocalDate):String{
            val i = ChronoUnit.DAYS.between(date, date2)
            val formatted = NumberFormat.getIntegerInstance().format(i)
            return "${formatted} total days"
        }

        @JvmStatic
        fun decadesBetween(date: LocalDate, date2: LocalDate):String{
            val i = ChronoUnit.DECADES.between(date, date2)
            val formatted = NumberFormat.getIntegerInstance().format(i)
            return "${formatted} total decades"
        }

        @JvmStatic
        fun centuriesBetween(date: LocalDate, date2: LocalDate):String{
            val i = ChronoUnit.CENTURIES.between(date, date2)
            val formatted = NumberFormat.getIntegerInstance().format(i)
            return "${formatted} total centuries"
        }

        @JvmStatic
        fun millenniaBetween(date: LocalDate, date2: LocalDate):String{
            val i = ChronoUnit.MILLENNIA.between(date, date2)
            val formatted = NumberFormat.getIntegerInstance().format(i)
            return "${formatted} total millennia"
        }



        @JvmStatic
        fun between(date: LocalDate, date2: LocalDate): String{
            var period = Period.between(date, date2)
            var years = NumberFormat.getIntegerInstance().format(period.years)
            var months = NumberFormat.getIntegerInstance().format(period.months)
            var days = NumberFormat.getIntegerInstance().format(period.days)
            return "$years years, $months months, and $days days."
        }

        @JvmStatic
        fun weeksBetween(date: LocalDate, date2: LocalDate): String{
            val answer = ChronoUnit.WEEKS.between(date, date2)
            val str = NumberFormat.getIntegerInstance().format(answer) + " weeks"
            return str
        }


        fun fullResponse(date: LocalDate, date2: LocalDate): String{
            return between(date, date2) + "\n"+ "--------------------------" + "\n" +
                    daysBetween(date, date2)  + "\n" +
                    weeksBetween(date, date2) + "\n" +
                    monthsBetween(date, date2) + "\n" +
                    yearsBetween(date, date2) + "\n" +
                    decadesBetween(date,date2) + "\n" +
                    centuriesBetween(date, date2) + "\n" +
                    millenniaBetween(date, date2) + "\n"
        }
    }

}