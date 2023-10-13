package com.example.nannamapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R
import com.example.nannamapp.ui.view.CreateRecipeActivity
import com.example.nannamapp.ui.view.IngredientFindedAdapater

class IngredientSelectedAdapter : RecyclerView.Adapter<IngredientSelectedAdapter.IngredientSelectedViewHolder>(){
    companion object{
        public var contextActivity = CreateRecipeActivity
    }
    private val ingredientSelected = mutableListOf<Ingredient>()
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientSelectedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientSelectedViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientSelectedViewHolder, position: Int) {
        val step = ingredientSelected[position]
        holder.bind(step)

        holder.itemView.setOnLongClickListener {
            // Iniciar la operación de arrastre
            itemTouchHelper.startDrag(holder)
            true
        }
    }

    override fun getItemCount(): Int {
        return ingredientSelected.size
    }

    //metodo llamado desde el otro adaptador
    fun addIngredientSelected(ingredient: Ingredient) {

        ingredientSelected.add(ingredient)
        notifyItemInserted(ingredientSelected.size - 1)
    }

    inner class IngredientSelectedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientTextView: TextView = itemView.findViewById(R.id.tvIngredientName)
        private val measureTextView: TextView = itemView.findViewById(R.id.tvMeasureUnit)
        private val deleteButton: Button = itemView.findViewById(R.id.btnDeleteIngredient)

        fun bind(ingredient:Ingredient ) {
            ingredientTextView.text = ingredient.ingredientname
            measureTextView.text = ingredient.measure
            deleteButton.setOnClickListener {
                // Aquí debes eliminar el elemento de la lista y notificar al adaptador
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    ingredientSelected.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    // Método del drag and drop para reorganizar los elementos
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = ingredientSelected.removeAt(fromPosition)
        ingredientSelected.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    //llamada desde la actividad
    fun validateSeletions(): Boolean{

        return true
    }


    fun validateIngerdientsSelected() : Boolean{
        var bandListIngredients : Boolean = false
        if(ingredientSelected.size != 0){
            bandListIngredients = true
        }
            return bandListIngredients
    }
    fun getContextIngredientSelectedAdapter() : IngredientSelectedAdapter{
        return this
    }
}