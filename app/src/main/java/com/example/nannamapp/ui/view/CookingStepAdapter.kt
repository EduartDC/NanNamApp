package com.example.nannamapp.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R

class CookingStepAdapter :RecyclerView.Adapter<CookingStepAdapter.StepViewHolder>(){

    private val steps = mutableListOf<String>()
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]
        holder.bind(step)

        holder.itemView.setOnLongClickListener {
            // Iniciar la operación de arrastre
            itemTouchHelper.startDrag(holder)
            true
        }
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    fun addStep(step: String) {
        Log.d("PASO" ,step)
        steps.add(step)
        notifyItemInserted(steps.size - 1)
    }

    inner class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stepTextView: TextView = itemView.findViewById(R.id.stepTextView)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(step: String) {
            stepTextView.text = step
            deleteButton.setOnClickListener {
                // Aquí debes eliminar el elemento de la lista y notificar al adaptador
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    steps.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    // Método del drag and drop para reorganizar los elementos
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = steps.removeAt(fromPosition)
        steps.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun setTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }
}