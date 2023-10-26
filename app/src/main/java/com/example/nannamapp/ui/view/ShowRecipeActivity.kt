package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nannamapp.R
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivityShowRecipeBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel

class ShowRecipeActivity : AppCompatActivity() {
    lateinit var binding :ActivityShowRecipeBinding
    private val getRecipeViewModel : RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //varibales para que dircio me pase el id de la receta
        val intent =intent
        var idRecipe = ""
        getRecipe(idRecipe)
        setListenerGetRecipe()
    }

    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
                loadInfoRecipe()
            }else{
                Toast.makeText(this,"ocurrio un fallo",Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun loadInfoRecipe() {
        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        binding.tvCategorieRecipe.text = RecipeProvider.recipeResponse.category.categoryName
        for(item in RecipeProvider.recipeResponse.ingredientList) {
            if(item.idIngredient == RecipeProvider.recipeResponse.recipe.idMainIngredient)
                binding.tvMainIngredient.text = item.ingredientname
        }
        binding.tvPortions.text = RecipeProvider.recipeResponse.recipe.portions.toString()

    }

    private fun getRecipe(idRecipe : String) {
        getRecipeViewModel.idRecipe = idRecipe
        getRecipeViewModel.getRecipe()
    }
}