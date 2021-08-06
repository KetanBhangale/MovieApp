package com.example.samplemovieapp.network

import com.example.samplemovieapp.model.MovieDetails
import com.example.samplemovieapp.model.MovieResult
import com.example.samplemovieapp.model.Team
import com.example.samplemovieapp.model.Trailers
import com.example.samplemovieapp.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPIService {
    @GET("/3/movie/popular?api_key="+Constant.API_KEY)
    suspend fun getMovieList(): MovieResult

    @GET("/3/movie/{movie_id}?api_key="+Constant.API_KEY)
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String
    ):MovieDetails

    @GET("/3/movie/{movie_id}/videos?api_key="+Constant.API_KEY)
    suspend fun getOfficialTrailers(
        @Path("movie_id") movieId: String
    ):Trailers

    @GET("/3/movie/{movie_id}/credits?api_key="+Constant.API_KEY)
    suspend fun getCastAndCrew(
        @Path("movie_id") movieId: String
    ):Team
}