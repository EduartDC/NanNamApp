package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.CookinginstructionDomain
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R
import com.example.nannamapp.data.model.RecipeHasIngredient

class StepShowRecipeAdapter : RecyclerView.Adapter<StepShowRecipeAdapter.StepShowRecipeAdapterViewHolder>() {
    private var steps = mutableListOf<CookinginstructionDomain>()
    private var number: Int = 0
    inner class StepShowRecipeAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val step : TextView = itemView.findViewById(R.id.tvDescriptionText)
        val numberStep : TextView = itemView.findViewById(R.id.tvNumberStep)
        val plus = number + 1
        fun bind(stepItem : CookinginstructionDomain) {

            step.text = stepItem.instruction
            numberStep.text = stepItem.step.toString()
        }
    }
    fun setItem(stepItem : CookinginstructionDomain){
        steps.add(stepItem)
        println("TAMANO: "+ steps.count())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepShowRecipeAdapter.StepShowRecipeAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.step_view_item, parent, false)
        return StepShowRecipeAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: StepShowRecipeAdapter.StepShowRecipeAdapterViewHolder,
        position: Int
    ) {
        val step = steps[position]
        holder.bind(step)
    }

    override fun getItemCount(): Int {
        return steps.count()
    }
}