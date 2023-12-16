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

        // Inicializar el adaptador antes de usarlo
        adapter = IngredientListAdapter(ingredientList)

        recyclerView = findViewById(R.id.recyclerView)
        btnClearAllIngredients = findViewById(R.id.btnClear)

        // Recuperar la lista de ingredientes de SharedPreferences
        ingredientList = loadCachedIngredientList()

        // Log para verificar la lista de ingredientes después de asignarla
        Log.d("IngredientListActivity", "Ingredient List (After): $ingredientList")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Actualizar el adaptador después de asignar la lista
        adapter.setItems(ingredientList)

        btnClearAllIngredients.setOnClickListener {
            adapter.clearItems()
            saveIngredientListToSharedPreferences(adapter.getItems())
        }
    }

    override fun onResume() {
        super.onResume()

        // Log para verificar la lista de ingredientes al reanudar la actividad
        Log.d("IngredientListActivity", "Ingredient List (onResume): $ingredientList")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar la lista de ingredientes en el estado para manejar cambios de configuración
        outState.putParcelableArrayList("ingredientList", ArrayList(ingredientList))
    }

    private fun loadCachedIngredientList(): MutableList<Ingredient> {
        // Obtener lista desde SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val jsonList = sharedPreferences.getString("ingredientListKey", null)

        // Logs para depuración
        Log.d("IngredientListActivity", "JSON List from SharedPreferences: $jsonList")

        // Si la lista no es nula, deserializarla y asignarla a ingredientList
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
