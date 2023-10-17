package com.example.nannamapp.ui.view

import android.R
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.nannamapp.databinding.ActivityConsultCookbookBinding
import com.example.nannamapp.ui.viewModel.CookBookViewModel


class ConsultCookbookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultCookbookBinding
    private val cookbookViewModel: CookBookViewModel by viewModels()
    private lateinit var recipeAdapter: CookBookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultCookbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicia la carga de datos
        cookbookViewModel.onCreate()

        // Configura el RecyclerView
        //binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(com.example.nannamapp.R.id.recyclerview)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        // Inicializa el adaptador
        recipeAdapter = CookBookAdapter()
        binding.recyclerview.adapter = recipeAdapter

        // Observa los cambios en la lista de recetas
        cookbookViewModel.cookBookModel.observe(this, Observer { recipes ->
            recipes?.let {
                recipeAdapter.setData(it)
            }
        })
    }

}