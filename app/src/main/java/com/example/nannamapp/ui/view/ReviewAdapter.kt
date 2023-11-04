package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.CookinginstructionDomain
import com.example.nannamapp.R
import com.example.nannamapp.data.model.ReviewDomain

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.reviewAdapterViewHolder>(){
    private var reviews = mutableListOf<ReviewDomain>()
    inner class reviewAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val rating : TextView = itemView.findViewById(R.id.tvRating)
        val description : TextView = itemView.findViewById(R.id.tvReview)

        fun bind(reviewItem : ReviewDomain,) {

            rating.text = reviewItem.review
            description.text = reviewItem.rate.toString() + "/5"
            //  notifyItemInserted(ingredientsName.count())
        }
    }
    fun setItem(reviewItem : ReviewDomain){
        reviews.add(reviewItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewAdapter.reviewAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review_show_recipe, parent, false)
        return reviewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReviewAdapter.reviewAdapterViewHolder,
        position: Int
    ) {
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviews.count()
    }
}