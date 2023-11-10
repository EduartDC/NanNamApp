package com.example.nannamapp.ui.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R

class IngredientListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnClearAllIngredients: Button
    private lateinit var adapter: IngredientListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)

        recyclerView = findViewById(R.id.recyclerView)
        btnClearAllIngredients = findViewById(R.id.btnClear)

        val ingredientList = intent.getParcelableArrayListExtra<Ingredient>("ingredientList")?.toMutableList() ?: mutableListOf()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = IngredientListAdapter(ingredientList)
        recyclerView.adapter = adapter

        btnClearAllIngredients.setOnClickListener {
            adapter.clearItems()
        }
    }
}
