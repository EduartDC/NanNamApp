package com.example.nannamapp.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Category
import com.example.nannamapp.R
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.data.model.UserPreferenceProvider

class UserPreferenceAdapter : RecyclerView.Adapter<UserPreferenceAdapter.preferenceAdapterViewHolder>(){
    private var categories = mutableListOf<Category>()

    inner class preferenceAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val rbCategory : CheckBox = itemView.findViewById(R.id.rbCategory)


        fun bind(categoryItem : Category,) {

            rbCategory.text = categoryItem.categoryName
            for ( temp in  UserPreferenceProvider.prefererence.userpreferences){
                if (categoryItem.idCategory.equals(temp.idCategory)){
                    rbCategory.isChecked = true
                }
            }

        }
    }
    fun setItem(categoryItem : Category){
        categories.add(categoryItem)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserPreferenceAdapter.preferenceAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_preferences, parent, false)
        return preferenceAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserPreferenceAdapter.preferenceAdapterViewHolder,
        position: Int
    ) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }
}