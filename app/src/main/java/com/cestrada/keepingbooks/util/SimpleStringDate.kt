package com.cestrada.keepingbooks.util

import android.util.Log
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SimpleStringDate @Throws(Exception::class) constructor(val value: String){
    init {
        val simpleFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        var isValidFormat = false
        try {
            simpleFormat.parse(value)
            isValidFormat = true
        } catch (e: ParseException) {}

        require(isValidFormat){"The format need to comply with: yyyy-MM-dd HH:mm:ss"}
    }

    fun getTimePassedUntilToday(): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val dateToday = Date()
        val datePayment = dateFormat.parse(value)

        val diff: Long = dateToday.time - datePayment!!.time
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        Log.d("MyPaymentRecyclerViewAdapter", "onBindViewHolder: $diff $seconds $minutes $hours $days")

        var date = "just now"
        when {
            (days > 0)      -> {
                date = when{
                    days in 31..364 -> "${days/30} months ago"
                    days > 364 -> "${days/365} years ago"
                    else -> "$days days ago"
                }
            }
            (hours > 0)     -> date = "$hours hours ago"
            (minutes > 0)   -> date = "$minutes min ago"
        }

        return date
    }

    override fun toString(): String {
        return value
    }
}