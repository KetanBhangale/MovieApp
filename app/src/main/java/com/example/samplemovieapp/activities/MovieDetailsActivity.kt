package com.example.samplemovieapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.samplemovieapp.adapters.CastAndCrewAdapter
import com.example.samplemovieapp.adapters.ITrailerAdapter
import com.example.samplemovieapp.adapters.TrailerAdapter
import com.example.samplemovieapp.databinding.ActivityMainDetailsBinding
import com.example.samplemovieapp.model.Genre
import com.example.samplemovieapp.model.MovieDetails
import com.example.samplemovieapp.repositories.viewmodel.MovieViewModel
import com.example.samplemovieapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_details.*

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity(), ITrailerAdapter {
 private var _binding: ActivityMainDetailsBinding?=null
    val binding:ActivityMainDetailsBinding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    lateinit var movieId:String
    lateinit var trailerAdapter: TrailerAdapter
lateinit var castAndCrewAdapter: CastAndCrewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getStringExtra("movieId").toString()
        Log.i("main","$movieId")

        movieViewModel.getMovieDetails(movieId)
        movieViewModel.getMovieTrailers(movieId)
        movieViewModel.getMovieCastAndCrews(movieId)

        movieViewModel.movieDetails.observe(this, Observer { response->
            Log.i("main","${response.toString()}")
            setUpUI(response)
        })


    }

    private fun setUpUI(movieDetails: MovieDetails) {
        _binding?.apply {
            Glide.with(this@MovieDetailsActivity).load(Constant.IMAGE_PATH+movieDetails.backdrop_path)
                .into(backgroundImage)
            Glide.with(this@MovieDetailsActivity).load(Constant.IMAGE_PATH+movieDetails.poster_path)
                .into(moviePoster)
            movieTitle.text = movieDetails.title
            releaseDate.text = Constant.convertDate(movieDetails.release_date)
            releaseYear.text = "(" +movieDetails.release_date.split("-")[0] +")"

            if (movieDetails.tagline.equals("")|| movieDetails.tagline==null){
                tagline.visibility = View.GONE
            }else{
                tagline.visibility = View.VISIBLE
                tagline.text = movieDetails.tagline
            }
            overviewText.text = movieDetails.overview
            duration.text = Constant.minutesToHrs(movieDetails.runtime)
            type.text = getMovieTypes(movieDetails.genres)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                status.text = Html.fromHtml("<b>Status: </b>"+movieDetails.status, Html.FROM_HTML_MODE_COMPACT)
                budget.text = Html.fromHtml("<b>Budget: </b>$"+movieDetails.budget, Html.FROM_HTML_MODE_COMPACT)
                revenue.text = Html.fromHtml("<b>Revenue: </b>$"+movieDetails.revenue, Html.FROM_HTML_MODE_COMPACT)
                language.text = Html.fromHtml("<b>Original Language: </b>"+movieDetails.spoken_languages[0].name, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Status: </b>"+movieDetails.status)
                budget.text = Html.fromHtml("<b>Budget: </b>$"+movieDetails.budget)
                revenue.text = Html.fromHtml("<b>Revenue: </b>$"+movieDetails.revenue)
                language.text = Html.fromHtml("<b>Original Language: </b>"+movieDetails.spoken_languages[0].name)
            }
        }
        _binding!!.movieCast.setOnClickListener {
            val intent = Intent(applicationContext, TestActivity::class.java)
            intent.putExtra("movieId", movieId)
            startActivity(intent)
        }
        setUpRVForCastAndCrews()
        setUpRVForTrailers()
    }

    private fun setUpRVForCastAndCrews() {
        castAndCrewAdapter = CastAndCrewAdapter()
        _binding!!.recyclerViewCast.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            hasFixedSize()
            adapter = castAndCrewAdapter
        }
        movieViewModel.movieCastsAndCrews.observe(this, Observer { response->
            Log.i("main","${response.toString()}")
            castAndCrewAdapter.castList = response.cast
            castAndCrewAdapter.crewList = response.crew
        })

    }

    private fun setUpRVForTrailers() {
        trailerAdapter = TrailerAdapter(this)
        _binding!!.recyclerViewTrailers.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailsActivity)
            hasFixedSize()
            adapter = trailerAdapter
        }
        movieViewModel.movieTrailers.observe(this, Observer { response->
            trailerAdapter.trailerList = response
        })
    }

    private fun getMovieTypes(genres:List<Genre>):String{
        var genresTypes = "⦿"
        var count=1
        for (i in genres){
            if (count==genres.size) {
                genresTypes += " ${i.name}"

            }else{
                genresTypes += " ${i.name},"
                count++
            }
        }
        return genresTypes
    }

    override fun onTrailerClicked(key: String) {
        val intent = Intent(applicationContext, VideoPlayerActivity::class.java)
        intent.putExtra("key",key)
        startActivity(intent)
    }
}