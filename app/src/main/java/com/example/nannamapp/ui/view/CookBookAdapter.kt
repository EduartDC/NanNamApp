package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R
import com.example.nannamapp.data.model.RecipeProvider

class CookBookAdapter: RecyclerView.Adapter<CookBookAdapter.ViewHolder>() {
    val recipes = RecipeProvider.cookBook
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        holder.image.setImageResource(R.drawable.sam)
        holder.number.text = (position + 1).toString()
        holder.songName.text = songs[position]
        holder.artistName.text = "Eminem"
        holder.time.text = times[position]
        */
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        /*
        var image: ImageView
        var number: TextView
        var songName: TextView
        var artistName: TextView
        var time: TextView

        init{
            image = itemView.findViewById(R.id.image)
            number = itemView.findViewById(R.id.number)
            songName = itemView.findViewById(R.id.songName)
            artistName = itemView.findViewById(R.id.artist)
            time = itemView.findViewById(R.id.time)
        }
         */
    }
}