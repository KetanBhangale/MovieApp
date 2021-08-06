package com.example.samplemovieapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemovieapp.R
import com.example.samplemovieapp.adapters.CastAndCrewAdapter
import com.example.samplemovieapp.databinding.ActivityMoviesBinding
import com.example.samplemovieapp.databinding.ActivityTestBinding
import com.example.samplemovieapp.repositories.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {
    private var _binding: ActivityTestBinding?=null
    val binding: ActivityTestBinding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    lateinit var castAndCrewAdapter: CastAndCrewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieId = intent.getStringExtra("movieId").toString()
        movieViewModel.getMovieCastAndCrews(movieId)
        setupRV()
    }

    private fun setupRV() {
        castAndCrewAdapter = CastAndCrewAdapter()
        _binding!!.recyclerView2.apply {
            layoutManager = LinearLayoutManager(this@TestActivity)
            hasFixedSize()
            adapter = castAndCrewAdapter
        }
        movieViewModel.movieCastsAndCrews.observe(this, Observer { response->
            Log.i("main","${response.toString()}")
            castAndCrewAdapter.castList = response.cast
            castAndCrewAdapter.crewList = response.crew
        })
    }
}