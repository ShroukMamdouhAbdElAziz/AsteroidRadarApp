package com.example.asteroidapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidapp.databinding.AsteroidItemBinding
import com.example.asteroidapp.models.Asteroid


// parameter of type Asteroid->unit
class AsteroidAdapter(   private val clickListener: (Asteroid) -> Unit): ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(AsteroidDiffCallBack()){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int ) {
        val dataItem = getItem(position)
        holder.onBind(dataItem , clickListener)
    }



    // binding logic
    class ViewHolder private constructor( private val binding: AsteroidItemBinding):RecyclerView.ViewHolder(binding.root){


        // bind the data
        fun onBind(data:Asteroid , clickListener: (Asteroid) -> Unit){

            binding.asteroid = data

            binding.root.setOnClickListener{
               clickListener.invoke(data)
            }

        }


        // create the viewHolder
        companion object{
            fun create(parent :ViewGroup):ViewHolder{
               val viewHolder = ViewHolder( AsteroidItemBinding.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               ))
                return viewHolder
            }
        }

    }
}

class AsteroidDiffCallBack:DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}