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
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.R
import com.example.nannamapp.databinding.CategoriesItemBinding

class SearchRecipeAdapterCategories(private val categoryList: List<Category>, private val onClickListener:(Category) -> Unit): RecyclerView.Adapter<SearchRecipeAdapterCategories.SearchRecipeViewHolderCategories>() {
    inner class SearchRecipeViewHolderCategories(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = CategoriesItemBinding.bind(itemView)
        fun render(category: Category, onClickListener:(Category) -> Unit){
            binding.categoriesTitle.text = category.categoryName
            itemView.setOnClickListener{
                onClickListener(category)
            }
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchRecipeViewHolderCategories {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.categories_item, viewGroup, false)
        return SearchRecipeViewHolderCategories(view)
    }
    override fun getItemCount(): Int= categoryList.size
    override fun onBindViewHolder(viewHolder: SearchRecipeViewHolderCategories, position: Int) {
        val item = categoryList[position]
        viewHolder.render(item, onClickListener)
    }
}