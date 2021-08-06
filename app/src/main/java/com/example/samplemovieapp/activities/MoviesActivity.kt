package com.example.samplemovieapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.samplemovieapp.adapters.IMovieListClickListener
import com.example.samplemovieapp.adapters.MovieListAdapter
import com.example.samplemovieapp.databinding.ActivityMoviesBinding
import com.example.samplemovieapp.repositories.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movies.*
@AndroidEntryPoint
class MoviesActivity : AppCompatActivity(), IMovieListClickListener {
    private var _binding: ActivityMoviesBinding?=null
    val binding:ActivityMoviesBinding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    lateinit var movieAdapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRV()
        showHideProgressBar()
        movieViewModel.getListOfMovies()
        movieViewModel.movieList.observe(this, Observer { response->
            Log.i("Main","${response.toString()}");
            showHideProgressBar()
            movieAdapter.movieList = response
        })
    }

    private fun showHideProgressBar() {
        if (progressBar1.visibility== View.VISIBLE){
            progressBar1.visibility = View.GONE
        }else{
            progressBar1.visibility = View.VISIBLE
        }

    }

    private fun setupRV() {
        movieAdapter = MovieListAdapter(this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MoviesActivity, 2)
            hasFixedSize()
            adapter = movieAdapter
        }
    }

    override fun onMoviePosterItemClicked(movieId: String) {
        val intent = Intent(applicationContext, MovieDetailsActivity::class.java)
        intent.putExtra("movieId",movieId)
        startActivity(intent)
    }
}