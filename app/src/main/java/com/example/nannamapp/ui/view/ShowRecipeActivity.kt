package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
        getRecipe(idRecipe)
        setListenerGetRecipe()
        listenerPrepareRecipe()
    }

    //lamada a CU-Preparar receta
    private fun listenerPrepareRecipe() {
        //val intent = Intent(this, ActividadDircio::class.java)
        startActivity(intent)
    }

    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
                Toast.makeText(this,"Al milloanso pai " + RecipeProvider.recipeResponse.recipe.idMainIngredient,Toast.LENGTH_SHORT).show()
                loadInfoRecipe()
            }else{
                Toast.makeText(this,"ocurrio un fallo: " + getRecipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()

            }
        }
    }

    //metodo solo para comprobar que me regrese las cosas,despues lo debo borrar
    private fun impresionPrueba() {
        println("INFORMACION DE OBJETO RECIPE")
        println("idRecipe: " +RecipeProvider.recipeResponse.recipe.idRecipe)
        println("recipeName: " +RecipeProvider.recipeResponse.recipe.recipeName)
        println("imageRecipeURL: " +RecipeProvider.recipeResponse.recipe.imageRecipeURL)
        println("User_idUser: " +RecipeProvider.recipeResponse.recipe.user_idUser)
        println("idMainIngredient: " +RecipeProvider.recipeResponse.recipe.idMainIngredient)
        println("preparationTime: " +RecipeProvider.recipeResponse.recipe.preparationTime)
        println("Portion: " +RecipeProvider.recipeResponse.recipe.portion)



        println("LISTA DE INSTRUCCIONES")
        println("IdCookingInstruction: " + RecipeProvider.recipeResponse.stepList[0].idCookingInstruction)
        println("RecipeIdRecipe: " + RecipeProvider.recipeResponse.stepList[0].recipeIdRecipe)
        println("Instruction: " + RecipeProvider.recipeResponse.stepList[0].instruction)
        println("Step: " + RecipeProvider.recipeResponse.stepList[0].step)
    }

    private fun loadInfoRecipe() {

        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        //binding.tvCategorieRecipe.text = RecipeProvider.recipeResponse.category.categoryName
        for(item in RecipeProvider.recipeResponse.ingredientList) {
            if(item.idIngredient == RecipeProvider.recipeResponse.recipe.idMainIngredient)
                binding.tvMainIngredient.text = item.ingredientname
        }
        binding.tvPortions.text = "" + RecipeProvider.recipeResponse.recipe.portion
        var ingredientsString : String = ""

        val adapter = IngredientsShowRecipeAdapter()
        binding.ingredientsFinded.layoutManager = LinearLayoutManager(this)
        binding.ingredientsFinded.adapter = adapter
        for(position in 0..RecipeProvider.recipeResponse.ingredientList.count()-1){
            adapter.setItem(RecipeProvider.recipeResponse.ingredientList[position],RecipeProvider.recipeResponse.ingredientAmounList[position])
        }

        val adapterSteps = StepShowRecipeAdapter()
        binding.rvIngredientSelected.layoutManager = LinearLayoutManager(this)
        binding.rvIngredientSelected.adapter = adapterSteps
        for(position in 0..RecipeProvider.recipeResponse.stepList.count()-1){
            println("asdasdas   " + RecipeProvider.recipeResponse.stepList[position].instruction)
            adapterSteps.setItem(RecipeProvider.recipeResponse.stepList[position])
        }


    }

    private fun getRecipe(idRecipe : String) {
        getRecipeViewModel.idRecipe = idRecipe
        getRecipeViewModel.getRecipe()
    }
}