package com.example.nannamapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.databinding.RecipesItemBinding
import com.example.nannamapp.R
import com.bumptech.glide.Glide
import com.example.namnam.data.model.Category

class SearchRecipeAdapterRecipe(private var recipeList: List<Recipe>, private val onClickListener:(Recipe) -> Unit): RecyclerView.Adapter<SearchRecipeAdapterRecipe.SearchRecipeAdapterRecipeViewHolder>(){
    inner class SearchRecipeAdapterRecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = RecipesItemBinding.bind(itemView)
        fun render(recipe: Recipe, onClickListener:(Recipe) -> Unit){
            binding.recipeTitle.text = recipe.recipeName
            binding.recipeDetails.text = recipe.preparationTime
            Glide.with(binding.recipeImage)
                .load(recipe.imageRecipeURL)
                .placeholder(R.drawable.tacos) // cambiar por placeholder mientras se carga la imagen
                .error(R.drawable.circle_shape) // cambiar por una imagen de error si no se puede cargar
                .into(binding.recipeImage)
            itemView.setOnClickListener(){
                onClickListener(recipe)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchRecipeAdapterRecipeViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recipes_item, viewGroup, false)
        return SearchRecipeAdapterRecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipeList.size
    override fun onBindViewHolder(viewHolder: SearchRecipeAdapterRecipeViewHolder, position: Int) {
        val item = recipeList[position]
        viewHolder.render(item, onClickListener)
    }
    fun updateRecipeList(recipeList: List<Recipe>){
        this.recipeList = recipeList
        notifyDataSetChanged()
    }
}