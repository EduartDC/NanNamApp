package com.example.nannamapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.nannamapp.R

interface OnRecipeClickListener {
    fun onRecipeClick(position: Int)
}
class SearchRecipeAdapterCategories: RecyclerView.Adapter<SearchRecipeAdapterCategories.SearchRecipeViewHolderCategories>() {
    val categories = arrayOf("Nombre", "Ingrediente","Tipo cocina", "Dieta")
    var onRecipeClickListener: OnRecipeClickListener? = null
    inner class SearchRecipeViewHolderCategories(itemView: View): RecyclerView.ViewHolder(itemView){

        var itemTitle: TextView
        init{

            itemTitle = itemView.findViewById(R.id.categories_title)
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
        viewHolder.itemTitle.text = categories[position]
        viewHolder.itemView.setOnClickListener {
            onRecipeClickListener?.onRecipeClick(position)
        }
    }
}