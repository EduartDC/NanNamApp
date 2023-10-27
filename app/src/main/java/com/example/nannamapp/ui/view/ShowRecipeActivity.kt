package com.example.nannamapp.ui.view

<<<<<<< Updated upstream
=======
import android.content.Intent
>>>>>>> Stashed changes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
<<<<<<< Updated upstream
import androidx.recyclerview.widget.LinearLayoutManager
=======
import com.example.nannamapp.R
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        getRecipe(idRecipe)
        setListenerGetRecipe()
        listenerPrepareRecipe()
    }

    //lamada a CU-Preparar receta
    private fun listenerPrepareRecipe() {
        //val intent = Intent(this, ActividadDircio::class.java)
        startActivity(intent)
=======
        println("ANTES: " + RecipeProvider.recipeResponse.stepList.count())
        getRecipe(idRecipe)
        setListenerGetRecipe()
>>>>>>> Stashed changes
    }

    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
                Toast.makeText(this,"Al milloanso pai " + RecipeProvider.recipeResponse.recipe.idMainIngredient,Toast.LENGTH_SHORT).show()
<<<<<<< Updated upstream
=======
                println("DESPUES: " + RecipeProvider.recipeResponse.stepList.count())
                println("IdCookingInstruction: " + RecipeProvider.recipeResponse.stepList[0].IdCookingInstruction)
                println("RecipeIdRecipe: " + RecipeProvider.recipeResponse.stepList[0].RecipeIdRecipe)
                println("Instruction: " + RecipeProvider.recipeResponse.stepList[0].Instruction)
                println("Step: " + RecipeProvider.recipeResponse.stepList[0].Step)
>>>>>>> Stashed changes
                loadInfoRecipe()
            }else{
                Toast.makeText(this,"ocurrio un fallo: " + getRecipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()

            }
        }
    }

<<<<<<< Updated upstream
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

=======
>>>>>>> Stashed changes
    private fun loadInfoRecipe() {

        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        //binding.tvCategorieRecipe.text = RecipeProvider.recipeResponse.category.categoryName
        for(item in RecipeProvider.recipeResponse.ingredientList) {
            if(item.idIngredient == RecipeProvider.recipeResponse.recipe.idMainIngredient)
                binding.tvMainIngredient.text = item.ingredientname
        }
<<<<<<< Updated upstream
        binding.tvPortions.text = "" + RecipeProvider.recipeResponse.recipe.portion
        var ingredientsString : String = ""
        for(position in 0..RecipeProvider.recipeResponse.ingredientList.count()-1){

        }
        val adapter = IngredientsShowRecipeAdapter()
        binding.ingredientsFinded.layoutManager = LinearLayoutManager(this)
        binding.ingredientsFinded.adapter = adapter


=======
        binding.tvPortions.text = "" + RecipeProvider.recipeResponse.recipe.Portion
    for(item in RecipeProvider.recipeResponse.stepList)
        println("paso: " + item.Instruction)
>>>>>>> Stashed changes
    }

    private fun getRecipe(idRecipe : String) {
        getRecipeViewModel.idRecipe = idRecipe
        getRecipeViewModel.getRecipe()
    }
}