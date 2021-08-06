package com.example.samplemovieapp.repositories

import com.example.samplemovieapp.model.MovieDetails
import com.example.samplemovieapp.model.MovieResult
import com.example.samplemovieapp.model.Team
import com.example.samplemovieapp.model.Trailers
import com.example.samplemovieapp.network.MovieAPIService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieAPIService: MovieAPIService) {

    suspend fun getAllMovieList(): MovieResult {
        return movieAPIService.getMovieList()
    }

    suspend fun getMovieDetails(movieId: String): MovieDetails {
        return movieAPIService.getMovieDetails(movieId)
    }

    suspend fun getMovieTrailers(movieId: String): Trailers {
        return movieAPIService.getOfficialTrailers(movieId)
    }

    suspend fun getCastAndCrews(movieId: String): Team {
        return movieAPIService.getCastAndCrew(movieId)
    }
}