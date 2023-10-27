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
        //varibales para que me pase el id de la receta
        val intent =intent
        var idRecipe = "RIJT6I55VQ"//id harcodeado, borrar cuando se haga la navegavilidad con CU buscar receta
        println("ANTES: " + RecipeProvider.recipeResponse.stepList.count())
        getRecipe(idRecipe)
        setListenerGetRecipe()
    }

    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
                Toast.makeText(this,"Al milloanso pai " + RecipeProvider.recipeResponse.recipe.idMainIngredient,Toast.LENGTH_SHORT).show()
                println("DESPUES: " + RecipeProvider.recipeResponse.stepList.count())
                println("IdCookingInstruction: " + RecipeProvider.recipeResponse.stepList[0].IdCookingInstruction)
                println("RecipeIdRecipe: " + RecipeProvider.recipeResponse.stepList[0].RecipeIdRecipe)
                println("Instruction: " + RecipeProvider.recipeResponse.stepList[0].Instruction)
                println("Step: " + RecipeProvider.recipeResponse.stepList[0].Step)
                loadInfoRecipe()
            }else{
                Toast.makeText(this,"ocurrio un fallo: " + getRecipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun loadInfoRecipe() {

        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        //binding.tvCategorieRecipe.text = RecipeProvider.recipeResponse.category.categoryName
        for(item in RecipeProvider.recipeResponse.ingredientList) {
            if(item.idIngredient == RecipeProvider.recipeResponse.recipe.idMainIngredient)
                binding.tvMainIngredient.text = item.ingredientname
        }
        binding.tvPortions.text = "" + RecipeProvider.recipeResponse.recipe.Portion
    for(item in RecipeProvider.recipeResponse.stepList)
        println("paso: " + item.Instruction)
    }

    private fun getRecipe(idRecipe : String) {
        getRecipeViewModel.idRecipe = idRecipe
        getRecipeViewModel.getRecipe()
    }
}