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

    private var ingredientList: MutableList<Ingredient> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)

        recyclerView = findViewById(R.id.recyclerView)
        btnClearAllIngredients = findViewById(R.id.btnClear)

        // Recuperar la lista de ingredientes de la intención o de la caché si está disponible
        ingredientList = savedInstanceState?.getParcelableArrayList<Ingredient>("ingredientList")?.toMutableList()
            ?: intent.getParcelableArrayListExtra<Ingredient>("ingredientList")?.toMutableList()
                    ?: mutableListOf()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = IngredientListAdapter(ingredientList)
        recyclerView.adapter = adapter

        btnClearAllIngredients.setOnClickListener {
            adapter.clearItems()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar la lista de ingredientes en el estado para manejar cambios de configuración
        outState.putParcelableArrayList("ingredientList", ArrayList(ingredientList))
    }
}
