package com.example.samplemovieapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemovieapp.R
import com.example.samplemovieapp.databinding.CastRowBinding
import com.example.samplemovieapp.databinding.CrewRowBinding

import com.example.samplemovieapp.model.Cast
import com.example.samplemovieapp.model.Crew
import com.example.samplemovieapp.model.Result
import com.example.samplemovieapp.model.Team
import com.example.samplemovieapp.utils.Constant

class CastAndCrewAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_CAST = 0
    private val TYPE_CREW = 1

    class CastViewHolder(val binding1:CastRowBinding): RecyclerView.ViewHolder(binding1.root){
        fun setCastData(cast:Cast){
            binding1.castName.text = cast.original_name
            binding1.movieCastName.text = cast.character
            if (cast.profile_path.isNotEmpty()) {
                Glide.with(binding1.castImage.context).load(Constant.IMAGE_PATH + cast.profile_path)
                    .into(binding1.castImage)
            }
        }
    }
    class CrewViewHolder(val binding2: CrewRowBinding): RecyclerView.ViewHolder(binding2.root){
        fun setCrewData(crew:Crew){
            binding2.crewName.text = crew.original_name
            binding2.designation.text = crew.job
            if (crew.profile_path != "" || crew.profile_path !=null) {
                Glide.with(binding2.crewImage.context).load(Constant.IMAGE_PATH + crew.profile_path)
                    .into(binding2.crewImage)
            }else{
                binding2.crewImage.setImageResource(R.drawable.ic_user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==TYPE_CAST){
            CastViewHolder(CastRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        } else{
            CrewViewHolder(CrewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("main","$position")
        if (holder is CastViewHolder) {
            (holder as CastViewHolder).setCastData(castList[position])
        }

        if (holder is CrewViewHolder) {
            val newPosition = position - castList.size
            (holder as CrewViewHolder).setCrewData(crewList[newPosition])
        }
    }

    override fun getItemCount()= castList.size + crewList.size


    override fun getItemViewType(position: Int): Int {
        if(position < castList.size){
            return TYPE_CAST;
        }

        if(position - castList.size < crewList.size){
            return TYPE_CREW;
        }

        return -1;
    }

    // Differ call back for Cast List
    private val diffCallback_cast = object: DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

    }

    private val differ_cast = AsyncListDiffer(this, diffCallback_cast)
    var castList: List<Cast>
        get() = differ_cast.currentList
        set(value) {
            differ_cast.submitList(value)
        }
// Differ call back for Crew List
    private val diffCallback_crew = object: DiffUtil.ItemCallback<Crew>(){
        override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem == newItem
        }

    }

    private val differ_crew = AsyncListDiffer(this, diffCallback_crew)
    var crewList: List<Crew>
        get() = differ_crew.currentList
        set(value) {
            differ_crew.submitList(value)
        }
}