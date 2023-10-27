package com.example.nannamapp.ui.view

import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R
import com.example.nannamapp.data.model.ShoppingItem

class ShoppingListAdapter(private val shoppingList: List<ShoppingItem>, private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder>() {

    private val selectedItems = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false)
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = shoppingList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    inner class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val productName: TextView = itemView.findViewById(R.id.productName)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(item: ShoppingItem) {
            checkbox.isChecked = selectedItems.contains(adapterPosition)
            productName.text = item.name
        }
    }

    fun toggleItemSelection(position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.add(position)
        }
        notifyItemChanged(position)
    }
}

