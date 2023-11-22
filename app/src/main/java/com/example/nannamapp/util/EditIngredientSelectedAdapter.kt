package com.example.nannamapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R
import com.example.nannamapp.ui.view.CreateRecipeActivity
import com.example.nannamapp.ui.view.EditIngredientAdapter
import com.example.nannamapp.ui.view.IngredientFindedAdapater

class EditIngredientSelectedAdapter : RecyclerView.Adapter<EditIngredientSelectedAdapter.EditIngredientSelectedViewHolder>() {
    companion object {
        public var contextActivity = CreateRecipeActivity

    }

    private lateinit var itemTouchHelper: ItemTouchHelper
    val ingredientSelected = mutableListOf<Ingredient>()
    var amountIngredientTest = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditIngredientSelectedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return EditIngredientSelectedViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditIngredientSelectedViewHolder, position: Int) {
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

    lateinit var contextAdapter: EditIngredientAdapter
    fun getInstanceAdapter(contextAdapter: EditIngredientAdapter) {//usada pra poder eliminar de la liste de ingredients principales
        this.contextAdapter = contextAdapter
    }

    inner class EditIngredientSelectedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientTextView: TextView = itemView.findViewById(R.id.tvIngredientName)
        val measureTextView: TextView = itemView.findViewById(R.id.tvMeasureUnit)
        val deleteButton: Button = itemView.findViewById(R.id.btnDeleteIngredient)
        val etMeasure: EditText = itemView.findViewById(R.id.etMeasure)

        fun bind(ingredient: Ingredient) {
            ingredientTextView.text = ingredient.ingredientname
            measureTextView.text = ingredient.measure
            if(amountIngredientTest != 0){
                etMeasure.setText(amountIngredientTest.toString())
                amountIngredientTest = 0
            }
            deleteButton.setOnClickListener {
                // Aquí debes eliminar el elemento de la lista y notificar al adaptador
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    ingredientSelected.removeAt(position)
                    notifyItemRemoved(position)
                    contextAdapter.mainIngerdientsList.removeAt(position)
                    //contextAdapter.binding.spMainIngredient.setSelection(contextAdapter.mainIngerdientsList.size-1)
                    if (contextAdapter.mainIngerdientsList.size == 0) {
                        contextAdapter.binding.spMainIngredient.adapter = null
                    }


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
    fun validateSeletions(): Boolean {

        return true
    }


    fun validateIngerdientsSelected(): Boolean {
        var bandListIngredients: Boolean = false
        if (ingredientSelected.size != 0) {
            bandListIngredients = true
        }
        return bandListIngredients
    }

    fun getContextIngredientSelectedAdapter(): EditIngredientSelectedAdapter {
        return this
    }


}