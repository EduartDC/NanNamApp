package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R
import com.example.nannamapp.util.RecipeAdapter

class SearchRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_search_recipe)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRecipes)
        val adapater = RecipeAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapater
    }
}