package com.example.nannamapp.ui.view

import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.RecipeDomain
import com.example.nannamapp.R
<<<<<<< HEAD
import com.example.nannamapp.data.model.RecipeProvider
import com.squareup.picasso.Picasso

class CookBookAdapter(private val onCardClickListener: CookBookAdapter.OnCardClickListener) : RecyclerView.Adapter<CookBookAdapter.ViewHolder>() {
    private var recipes: List<Recipe> = listOf()
=======

class CookBookAdapter : RecyclerView.Adapter<CookBookAdapter.ViewHolder>() {
    private var recipes: List<RecipeDomain> = listOf()
>>>>>>> Development

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        val imageUri = recipe.imageRecipeURL
        holder.itemView.setOnClickListener {
            onCardClickListener.onCardClick(position)
        }

        AsyncTask.execute {
            try {
                val bitmap = Picasso.get().load(imageUri).get()
                holder.image.post {
                    holder.image.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        holder.recipeName.text = recipe.recipeName
    }

    fun setData(newRecipes: List<RecipeDomain>) {
        recipes = newRecipes

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.ivRecipe)
        var recipeName: TextView = itemView.findViewById(R.id.tvRecipeName)

    }

    interface OnCardClickListener {
        fun onCardClick(position: Int)
    }
}
