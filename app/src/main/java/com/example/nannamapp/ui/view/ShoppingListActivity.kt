package com.example.nannamapp.ui.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R
import com.example.nannamapp.data.model.ShoppingItem

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnClearList: Button
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shopping_list_layout)

        recyclerView = findViewById(R.id.recyclerView)
        btnClearList = findViewById(R.id.btnClearList)

        // Configurar RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val shoppingList = createSampleShoppingList()
        adapter = ShoppingListAdapter(shoppingList) { position ->
            adapter.toggleItemSelection(position)
        }
        recyclerView.adapter = adapter

        // Manejar clic en el botón de borrado
        btnClearList.setOnClickListener {
            // Filtrar la lista para obtener solo los elementos no seleccionados
            val itemsToKeep = shoppingList.filter { !it.isSelected }

            // Limpiar la lista actual y agregar los elementos no seleccionados
            shoppingList.clear()
            shoppingList.addAll(itemsToKeep)

            // Notificar al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged()
        }
    }

    private fun createSampleShoppingList(): MutableList<ShoppingItem> {
        // Inicializar la lista con todos los elementos no seleccionados
        return mutableListOf(
            ShoppingItem("Tortillas 1", false),
            ShoppingItem("Pan 2", false),
            ShoppingItem("Cebolla 3", false)
        )
    }
}

