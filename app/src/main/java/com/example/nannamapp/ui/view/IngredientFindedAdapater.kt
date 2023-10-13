package com.example.nannamapp.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.IngredientProvider
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityCreateRecipeBinding

class IngredientFindedAdapater  : RecyclerView.Adapter<IngredientFindedAdapater.IngredientFindedViewHolder>(){

    private val IngredientsFindedList = mutableListOf<String>()
    private lateinit var itemTouchHelper: ItemTouchHelper

    //este metodo hace referencia al layout donde coloqué la barra de ingredientes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientFindedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_serch_bar_item, parent, false)
        return IngredientFindedViewHolder(view)

    }

    override fun onBindViewHolder(holder: IngredientFindedViewHolder, position: Int) {
        if(position != 0){
        val ingredient = IngredientsFindedList[position]
        holder.bind(ingredient)
        holder.itemView.setOnLongClickListener {
            // Iniciar la operación de arrastre
            itemTouchHelper.startDrag(holder)
            true
        }}
    }

    //usado para calcular el tamaño de la lista mutable y que actuliza el recycle view
    override fun getItemCount(): Int {
        return IngredientsFindedList.size
    }

    //metodo llamado desde mi activivdad para pasarle el string de busqueda
    fun searchIngredientMatch(textInput: String) {
        IngredientsFindedList.clear()

        // Agregar elementos que coinciden con el texto de búsqueda
        for (ingredient in IngredientProvider.ingredients) {
            if (!textInput.isNullOrEmpty() && ingredient.ingredientname.contains(textInput)) {
                IngredientsFindedList.add(ingredient.ingredientname)
            }
        }

        notifyDataSetChanged()
    }

    inner class IngredientFindedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val IngredientTextView: TextView = itemView.findViewById(R.id.textIngredientSearchBar)
        private val addButton: Button = itemView.findViewById(R.id.btnAddIngredient)

        fun bind(ingredient: String) {
            IngredientTextView.text = ingredient

            addButton.setOnClickListener {
                // Obtener la posición del elemento
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Eliminar el elemento de la lista y notificar el cambio
                    IngredientsFindedList.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}