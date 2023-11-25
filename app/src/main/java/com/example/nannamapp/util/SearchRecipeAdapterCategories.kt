package com.example.nannamapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.R
import com.example.nannamapp.databinding.CategoriesItemBinding

interface OnRecipeClickListener {
    fun onRecipeClick(position: Int)
}
class SearchRecipeAdapterCategories: RecyclerView.Adapter<SearchRecipeAdapterCategories.SearchRecipeViewHolderCategories>() {
    val categories = arrayOf("Nombre", "Ingrediente","Tipo cocina", "Dieta")
    inner class SearchRecipeViewHolderCategories(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = CategoriesItemBinding.bind(itemView)
        fun render(categorie: String){
            binding.categoriesTitle.text = categorie
            itemView.setOnClickListener(){
                Toast.makeText(binding.categoriesTitle.context, binding.categoriesTitle.text,Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchRecipeViewHolderCategories {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.categories_item, viewGroup, false)
        return SearchRecipeViewHolderCategories(view)
    }
    override fun getItemCount(): Int {
        return categories.size
    }
    override fun onBindViewHolder(viewHolder: SearchRecipeViewHolderCategories, position: Int) {
        val item = categories[position]
        viewHolder.render(item)
    }
}