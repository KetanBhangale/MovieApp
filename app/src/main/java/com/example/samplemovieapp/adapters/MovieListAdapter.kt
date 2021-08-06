package com.example.samplemovieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemovieapp.databinding.MovieListRowBinding
import com.example.samplemovieapp.model.Movie
import com.example.samplemovieapp.model.MovieResult
import com.example.samplemovieapp.utils.Constant

class MovieListAdapter(private val listener: IMovieListClickListener):RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    class MovieListViewHolder(val binding:MovieListRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(MovieListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movieItem = movieList[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context).load(Constant.IMAGE_PATH+movieItem.poster_path)
                .into(moviePoster)
            movieTitle.text = movieItem.title
            releaseDate.text = Constant.convertDate(movieItem.release_date)
        }
        holder.binding.moviePoster.setOnClickListener {
                listener.onMoviePosterItemClicked(movieItem.id.toString())
        }
    }

    override fun getItemCount()=movieList.size

    private val diffCallback = object: DiffUtil.ItemCallback<Movie>(){


        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var movieList:List<Movie> get() = differ.currentList
    set(value) {differ.submitList(value)}
}

interface IMovieListClickListener {
    fun onMoviePosterItemClicked(movieId:String)
}