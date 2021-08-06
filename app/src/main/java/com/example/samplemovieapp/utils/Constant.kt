package com.example.samplemovieapp.utils

import java.text.SimpleDateFormat

class Constant {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org"
        const val API_KEY = "add_your_theMovieDB_API_Key"
        const val IMAGE_PATH = "https://image.tmdb.org/t/p/w500"

        fun convertDate(date: String): String {
            val formatter1 = SimpleDateFormat("yyyy-MM-dd")
            val formatter2 = SimpleDateFormat("dd-MMM-yyyy")
            val newDate = formatter1.parse(date)
            return formatter2.format(newDate)
        }

        fun minutesToHrs(min:Int): String{
            val hours  = min /60
            val minutes = min % 60
            val formattedMinutes = String.format("%02d", minutes )
            var str = "$hours"+"h $formattedMinutes"+"m"
            return str
        }

    }
}