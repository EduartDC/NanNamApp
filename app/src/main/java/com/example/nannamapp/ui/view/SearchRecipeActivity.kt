package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nannamapp.R
import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivitySearchRecipeBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.example.nannamapp.util.SearchRecipeAdapterCategories
import com.example.nannamapp.util.SearchRecipeAdapterRecipe
import kotlinx.coroutines.launch

class SearchRecipeActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchRecipeBinding
    private val recipesViewModel : RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initObservables()
        //initListeners()
        getAllRecipes()
    }

    private fun getAllRecipes() {
        recipesViewModel.getRecipesList()
        recipesViewModel.recipesViewModel.observe(this){
            initListeners()
            initRecyclerView()
        }
    }

    private fun initListeners() {
        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                recipesViewModel.getRecipesList()
            }
        }
    }

    fun initRecyclerView(){
        val recipeProvider = RecipeProvider
        val x = SearchRecipeAdapterRecipe(recipeProvider.recipeList)
        binding.recyclerviewRecipes.layoutManager = LinearLayoutManager(this)
        x.binding2= this
        binding.recyclerviewRecipes.adapter = x
    }
    private fun initObservables() {
        val recyclerViewCategories = findViewById<RecyclerView>(R.id.recyclerviewCategories)
        val categoriesAdapter = SearchRecipeAdapterCategories()
        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategories.adapter = categoriesAdapter
    }
}