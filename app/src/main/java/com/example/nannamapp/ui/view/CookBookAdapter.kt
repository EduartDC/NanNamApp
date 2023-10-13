package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.R
import com.example.nannamapp.data.model.RecipeProvider

class CookBookAdapter : RecyclerView.Adapter<CookBookAdapter.ViewHolder>() {
    private var recipes: List<Recipe> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.image.setImageResource(R.drawable.tacos)
        holder.recipeName.text = recipe.recipeName
        holder.rating.text = "4.5"
    }

    fun setData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.ivRecipe)
        var recipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        var rating: TextView = itemView.findViewById(R.id.tvRating)
    }
}
