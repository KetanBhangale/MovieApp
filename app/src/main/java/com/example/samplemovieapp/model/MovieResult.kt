package com.example.samplemovieapp.model

data class MovieResult(
    val page: Int,
    var results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
}