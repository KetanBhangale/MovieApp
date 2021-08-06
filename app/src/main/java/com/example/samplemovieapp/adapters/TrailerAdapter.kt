package com.example.samplemovieapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemovieapp.databinding.TrailerRowBinding
import com.example.samplemovieapp.model.Result
import com.example.samplemovieapp.model.Trailers

class TrailerAdapter(private val listener: ITrailerAdapter) : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    class TrailerViewHolder(val binding: TrailerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        return TrailerViewHolder(TrailerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = trailerList[position]

        holder.binding.trailerText.text = trailer.name
        holder.binding.trailerText.setOnClickListener {
            listener.onTrailerClicked(trailer.key)
        }
    }

    override fun getItemCount():Int{
        Log.i("main trailerList.size","${trailerList.size}")
        return trailerList.size
    }


    private val diffCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var trailerList: List<Result>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }
}

interface ITrailerAdapter{
    fun onTrailerClicked(key:String)
}