package com.example.nannamapp.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnClearAllIngredients: Button
    private lateinit var adapter: IngredientListAdapter

    private var ingredientList: MutableList<Ingredient> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_list)

        adapter = IngredientListAdapter(ingredientList)

        recyclerView = findViewById(R.id.recyclerView)
        btnClearAllIngredients = findViewById(R.id.btnClear)

        ingredientList = loadCachedIngredientList()

        Log.d("IngredientListActivity", "Ingredient List (After): $ingredientList")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setItems(ingredientList)

        btnClearAllIngredients.setOnClickListener {
            adapter.clearItems()
            saveIngredientListToSharedPreferences(adapter.getItems())
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d("IngredientListActivity", "Ingredient List (onResume): $ingredientList")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("ingredientList", ArrayList(ingredientList))
    }

    private fun loadCachedIngredientList(): MutableList<Ingredient> {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val jsonList = sharedPreferences.getString("ingredientListKey", null)

        // Logs para depuración
        Log.d("IngredientListActivity", "JSON List from SharedPreferences: $jsonList")

        return if (jsonList != null) {
            val cachedList = Gson().fromJson<MutableList<Ingredient>>(
                jsonList,
                object : TypeToken<MutableList<Ingredient>>() {}.type
            )

            // Logs para depuración
            Log.d("IngredientListActivity", "Cached List: $cachedList")

            cachedList
        } else {
            mutableListOf()
        }
    }

    private fun saveIngredientListToSharedPreferences(ingredientList: List<Ingredient>) {
        val gson = Gson()
        val json = gson.toJson(ingredientList)
        val editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
        editor.putString("ingredientListKey", json)
        editor.apply()
    }
}
