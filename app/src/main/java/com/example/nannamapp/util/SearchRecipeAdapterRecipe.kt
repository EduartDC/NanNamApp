package com.example.nannamapp.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.databinding.RecipesItemBinding
import com.example.nannamapp.R
import com.bumptech.glide.Glide
import com.example.nannamapp.ui.view.ShowRecipeActivity

class SearchRecipeAdapterRecipe(private val recipeList: List<Recipe>): RecyclerView.Adapter<SearchRecipeAdapterRecipe.SearchRecipeAdapterRecipeViewHolder>(){
    lateinit var binding2 : Context
    inner class SearchRecipeAdapterRecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = RecipesItemBinding.bind(itemView)



        fun render(recipe: Recipe){
            binding.recipeTitle.text = recipe.recipeName
            binding.recipeDetails.text = recipe.preparationTime
            Glide.with(binding.recipeImage)
                .load(recipe.imageRecipeURL)
                .placeholder(R.drawable.tacos) // cambiar por placeholder mientras se carga la imagen
                .error(R.drawable.circle_shape) // cambiar por una imagen de error si no se puede cargar
                .into(binding.recipeImage)
            itemView.setOnClickListener(){
                val intent = Intent(itemView.context, ShowRecipeActivity::class.java)
                itemView.context.startActivity(intent)
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
        viewHolder.render(item)
    }
}