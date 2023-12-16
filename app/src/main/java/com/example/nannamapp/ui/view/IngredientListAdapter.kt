package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R

class IngredientListAdapter(private val ingredientList: MutableList<Ingredient>) :
    RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredientList[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setItems(items: List<Ingredient>) {
        ingredientList.clear()
        ingredientList.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        ingredientList.clear()
        notifyDataSetChanged()
    }
    fun getItems(): List<Ingredient> {
        return ingredientList.toList()
    }

    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientName: TextView = itemView.findViewById(R.id.textViewIngredientName)

        fun bind(ingredient: Ingredient) {
            ingredientName.text = ingredient.ingredientname
        }
    }
}
