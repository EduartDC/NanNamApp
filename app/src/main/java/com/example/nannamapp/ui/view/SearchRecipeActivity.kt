package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.Recipe
import com.example.nannamapp.R
import com.example.nannamapp.data.RecipesRepository
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivitySearchRecipeBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.example.nannamapp.util.SearchRecipeAdapterCategories
import com.example.nannamapp.util.SearchRecipeAdapterRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchRecipeActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchRecipeBinding
    private lateinit var categoryAdapter : SearchRecipeAdapterCategories
    private lateinit var recipeAdapter : SearchRecipeAdapterRecipe
    private val recipesViewModel : RecipeViewModel by viewModels()
    private var idUser = "1"//id hardcodeado, borrar cuando cris tenga el id del usuario

    private var recipeMutableList: MutableList<Recipe> = RecipeProvider.recipeList.toMutableList()
    private var categoryMutableList: MutableList<Category> = CategoryProvider.categories.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        idUser = LoginProvider.login!!.idUser
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.searchEditText.addTextChangedListener {recipeName ->
            val recipesFiltered =
                recipeMutableList.filter { recipe ->
                    recipe.recipeName.lowercase().contains(recipeName.toString().lowercase())
                }
            recipeAdapter.updateRecipeList(recipesFiltered)

        }
        initObservables()
        initListeners()
        initRecyclerView()
    }

    private fun initListeners() {
        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                recipesViewModel.getRecipesList()
            }
        }

    }
    fun initRecyclerView() {
        val recipeProvider = RecipesRepository()
        binding.recyclerviewRecipes.layoutManager = LinearLayoutManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            recipeMutableList = recipeProvider.getRecipesList().toMutableList()
            withContext(Dispatchers.Main) {
                recipeAdapter = SearchRecipeAdapterRecipe(recipeMutableList){ recipe: Recipe -> onRecipeSelected(recipe) }
                binding.recyclerviewRecipes.adapter = recipeAdapter
            }
            categoryMutableList = recipeProvider.getCategoryList().toMutableList()
            withContext(Dispatchers.Main){
                categoryAdapter = SearchRecipeAdapterCategories(categoryMutableList){ category: Category -> onCategorySelected(category) }
                binding.recyclerviewCategories.adapter = categoryAdapter
            }
        }

    }
    private fun initObservables() {
        val recyclerViewCategories = findViewById<RecyclerView>(R.id.recyclerviewCategories)
        recyclerViewCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }
    private fun onCategorySelected(category:Category){
        val recipeProvider = RecipesRepository()

        if(category.idCategory!="1"){
            var recipesFiltered : List<Recipe>
            CoroutineScope(Dispatchers.IO).launch {
                recipesFiltered = recipeProvider.getRecipesListByCategory(category.idCategory)
                withContext(Dispatchers.Main) { recipeAdapter.updateRecipeList(recipesFiltered) }
            }
        }else{
            initRecyclerView()
        }
        Toast.makeText(this, category.categoryName, Toast.LENGTH_SHORT).show()
    }

    private fun onRecipeSelected(recipe: Recipe){
        val idRecipe = recipe.idRecipe
        // Crea un Intent
        val intent = Intent(this, ShowRecipeActivity::class.java)

        // Agrega el String al Intent como un extra
        intent.putExtra("key_idRecipe", idRecipe)

        // Inicia la nueva Activity
        startActivity(intent)
    }
}