package com.example.samplemovieapp.repositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemovieapp.model.*
import com.example.samplemovieapp.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository):ViewModel(){
    private var _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList

    private var _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> get() = _movieDetails

    private var _movieTrailers = MutableLiveData<List<Result>>()
    val movieTrailers: LiveData<List<Result>> get() = _movieTrailers

    private var _movieCastsAndCrews = MutableLiveData<Team>()
    val movieCastsAndCrews: LiveData<Team> get() = _movieCastsAndCrews
//private var _movieCasts = MutableLiveData<List<Cast>>()
//    val movieCasts: LiveData<List<Cast>> get() = _movieCasts



     fun getListOfMovies() {
        viewModelScope.launch {
            _movieList.postValue(movieRepository.getAllMovieList().results)
        }
    }

    fun getMovieDetails(movie_id:String){
        viewModelScope.launch {
            _movieDetails.postValue(movieRepository.getMovieDetails(movie_id))
        }
    }

    fun getMovieTrailers(movie_id:String){
        viewModelScope.launch {
            val list:List<Result> = movieRepository.getMovieTrailers(movie_id).results
            val newList = list.filter { result->
                result.type=="Trailer" && result.name.contains("Official Trailer")
            }
            val newListMaxSize = newList.take(2)
            _movieTrailers.postValue(newListMaxSize)
        }
    }

    fun getMovieCastAndCrews(movie_id:String){
        viewModelScope.launch {
            val castAndCrew = movieRepository.getCastAndCrews(movie_id)
            val castList = castAndCrew.cast.take(5)
            val crewList = castAndCrew.crew.take(5)
            val filteredTeam = Team(castList,crewList, castAndCrew.id)
            _movieCastsAndCrews.postValue(filteredTeam)
        }
    }
}