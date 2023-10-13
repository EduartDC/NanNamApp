package com.example.nannamapp.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.IngredientProvider
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityCreateRecipeBinding
import com.example.nannamapp.util.IngredientSelectedAdapter

class IngredientFindedAdapater :
    RecyclerView.Adapter<IngredientFindedAdapater.IngredientFindedViewHolder>() {

    private var IngredientsFindedList = mutableListOf<String>()
    private lateinit var itemTouchHelper: ItemTouchHelper

    //este metodo hace referencia al layout donde coloqué la barra de ingredientes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientFindedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_serch_bar_item, parent, false)
        return IngredientFindedViewHolder(view)

    }

    override fun onBindViewHolder(holder: IngredientFindedViewHolder, position: Int) {
        val ingredient = IngredientsFindedList[position]
        holder.bind(ingredient)
        holder.itemView.setOnLongClickListener {
            // Iniciar la operación de arrastre
            itemTouchHelper.startDrag(holder)
            true
        }
    }

    //usado para calcular el tamaño de la lista mutable y que actuliza el recycle view
    override fun getItemCount(): Int {
        return IngredientsFindedList.size
    }

    public lateinit var binding: ActivityCreateRecipeBinding
    public lateinit var contextActivity: Context

    //metodo llamado desde mi activivdad para pasarle el string de busqueda
    fun searchIngredientMatch(
        textInput: String,
        binding: ActivityCreateRecipeBinding,
        contextActivity: Context
    ) {
        this.contextActivity = contextActivity
        this.binding = binding
        IngredientsFindedList.clear()
        // Agregar elementos que coinciden con el texto de búsqueda
        for (ingredient in IngredientProvider.ingredients) {
            if (!textInput.isNullOrEmpty() && ingredient.ingredientname.contains(textInput)) {
                IngredientsFindedList.add(ingredient.ingredientname)
            }
        }
        notifyDataSetChanged()
    }

    private var mainIngerdientsList: MutableList<String> = mutableListOf()
    private lateinit var mainIngredientAdapter: ArrayAdapter<String>//

    inner class IngredientFindedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val IngredientTextView: TextView =
            itemView.findViewById(R.id.textIngredientSearchBar)
        private val addButton: Button = itemView.findViewById(R.id.btnAddIngredient)
        private var ingredientString : String = ""
        fun bind(ingredientString: String) {
            this.ingredientString = ingredientString
            if (IngredientsFindedList.size != 0) {
                IngredientTextView.text = ingredientString
                var band: Boolean = false
                addButton.setOnClickListener {
                    mainIngerdientsList.forEach { item ->
                        if (item.equals(ingredientString))
                            band = true
                    }
                    if (band == false){
                        addMainIngredient()

                    }
                    else
                        Toast.makeText(contextActivity,"Este ingrediente ay se encuentra seleccioando",Toast.LENGTH_SHORT).show()

                }
            }
        }

        private fun addMainIngredient() {//agregar a lista para en checkBox
            mainIngerdientsList.add(IngredientTextView.text.toString())
            mainIngredientAdapter = ArrayAdapter<String>(
                contextActivity,
                android.R.layout.simple_spinner_dropdown_item,
                mainIngerdientsList
            )
            Log.d("addMAIN","antes del if")
            IngredientProvider.ingredients.forEach { item ->
                if (item.ingredientname.equals(ingredientString)) {
                    Log.d("SIMON","seleccionado")
                    adapteringredientSelected.addIngredientSelected(item)
                }
            }
            binding.spMainIngredient.adapter = mainIngredientAdapter
        }
    }

    //iniciarlizar adpadtador de inredientes con medidas
    private lateinit var adapteringredientSelected: IngredientSelectedAdapter
    fun setIngredientSelectedAdapter(
        binding: ActivityCreateRecipeBinding,
        contextActivity: Context
    ) {
        Log.d("LISTA", "Agregando a seleccion")
        this.contextActivity = contextActivity
        this.binding = binding
        binding.rvIngredientSelected.layoutManager = LinearLayoutManager(contextActivity)
        adapteringredientSelected = IngredientSelectedAdapter()

        binding.rvIngredientSelected.adapter = adapteringredientSelected
    }
    //usado para pasar el contexto de otro adapter a la actividad
    fun getIngredientSelectedList():IngredientSelectedAdapter{
        var contextIngredientSelectedAdapter : IngredientSelectedAdapter = adapteringredientSelected.getContextIngredientSelectedAdapter()
        return contextIngredientSelectedAdapter
    }
}