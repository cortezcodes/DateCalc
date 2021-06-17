package com.example.datecalc

import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit


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
        fun between(date: LocalDate, date2: LocalDate): String{
            var period = Period.between(date, date2)
            var years = NumberFormat.getIntegerInstance().format(period.years)
            var months = NumberFormat.getIntegerInstance().format(period.months)
            var days = NumberFormat.getIntegerInstance().format(period.days)

            var str = years + " years," + months + " months, and " + days + " days."
            return str
        }

        @JvmStatic
        fun weekdaysBetween(date: LocalDate, date2: LocalDate): String{
            val startW = date.dayOfWeek
            val endW = date2.dayOfWeek
            val days = ChronoUnit.DAYS.between(date, date2)
            val daysWithoutWeekends = days - 2 * ((days + startW.value)/7)
            var weekdays = if (startW == DayOfWeek.SUNDAY) 1 else 0
            weekdays += if (endW == DayOfWeek.SUNDAY) 1 else 0
            val answer = daysWithoutWeekends + weekdays
            val str = NumberFormat.getIntegerInstance().format(answer) + " weekdays between"
            return str
        }

        fun fullResponse(date: LocalDate, date2: LocalDate): String{
            return between(date, date2) + "\n"+
                    yearsBetween(date, date2) + "\n" +
                    monthsBetween(date, date2) + "\n" +
                    daysBetween(date, date2)  + "\n" +
                    decadesBetween(date,date2) + "\n" +
                    weekdaysBetween(date, date2)
        }
    }

}