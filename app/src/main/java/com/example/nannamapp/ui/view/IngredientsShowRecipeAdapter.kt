package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R
import com.example.nannamapp.data.model.RecipeHasIngredient

class IngredientsShowRecipeAdapter  :
    RecyclerView.Adapter<IngredientsShowRecipeAdapter.IngredientsShowRecipeAdapterViewHolder>() {
   private var ingredientsName = mutableListOf<Ingredient>()
    private var ingredientsAmount = mutableListOf<RecipeHasIngredient>()

    //genera el item del xml
    inner class IngredientsShowRecipeAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nameIngredient :TextView = itemView.findViewById(R.id.tvIngredientName)
        val amountIngredient : TextView = itemView.findViewById(R.id.etAmountIngredient)
        val measureIngredient :TextView = itemView.findViewById(R.id.etMeasureIngredient)

        fun bind(ingredientItem : Ingredient, amountItem : RecipeHasIngredient) {

            nameIngredient.text = ingredientItem.ingredientname
            amountIngredient.text =  amountItem.amount.toString()
            measureIngredient.text = ingredientItem.measure
          //  notifyItemInserted(ingredientsName.count())
        }
   }

    fun setItem(ingredientItem : Ingredient, amountItem : RecipeHasIngredient){
        ingredientsName.add(ingredientItem)
        ingredientsAmount.add(amountItem)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientsShowRecipeAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredients_showrecipe, parent, false)
        return IngredientsShowRecipeAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: IngredientsShowRecipeAdapter.IngredientsShowRecipeAdapterViewHolder,
        position: Int
    ) {
        val ingredient = ingredientsName[position]
        val amount = ingredientsAmount[position]
        holder.bind(ingredient,amount)
    }

    override fun getItemCount(): Int {
        return ingredientsName.count()
    }
}