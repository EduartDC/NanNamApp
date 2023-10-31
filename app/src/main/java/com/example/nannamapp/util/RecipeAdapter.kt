package com.example.nannamapp.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R


class RecipeAdapter: RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    val titles = arrayOf("Recipe", "asas","Recipe", "asas","Recipe", "asas")
    val details = arrayOf("asdasd","asdasd","asdasd","asdasd","asdasd","asdasd")
    val images = intArrayOf(R.drawable.circle_red,R.drawable.circle_red,R.drawable.circle_red,R.drawable.circle_red,R.drawable.circle_red,R.drawable.circle_red)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init{
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_details)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_recipes, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemTitle.text = titles[position]
        viewHolder.itemDetail.text = details[position]
        viewHolder.itemImage.setImageResource(images[position])
    }
}