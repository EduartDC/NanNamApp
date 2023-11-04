package com.example.nannamapp.ui.view

import android.R
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivityPrepareRecipeBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PrepareRecipeActivity : AppCompatActivity() {

    lateinit var binding : ActivityPrepareRecipeBinding
    private val getRecipeViewModel : RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrepareRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent =intent
        var idRecipe = "RIJT6I55VQ"
        getRecipe(idRecipe)
        setListenerGetRecipe()

        val spinner: Spinner = findViewById(binding.cbPortions.id)
        val opciones = listOf("2", "4", "6", "8", "10")

        val adaptador = ArrayAdapter(this, R.layout.simple_spinner_item, opciones)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adaptador


        // Agregar el Listener del Spinner
        val adapter = IngredientsShowRecipeAdapter()
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val seleccion : Int = opciones[position].toInt()
                adapter.clear()
                adapter.portionCalculation(seleccion)
                binding.rvIngredients.adapter = adapter
                for(position in 0..RecipeProvider.recipeResponse.ingredientList.count()-1){
                    adapter.setItem(RecipeProvider.recipeResponse.ingredientList[position],RecipeProvider.recipeResponse.ingredientAmounList[position])
                }
                Toast.makeText(this@PrepareRecipeActivity, "Seleccionaste: $seleccion", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó ningún elemento
            }
        }

    }

    private fun getRecipe(idRecipe : String) {
        getRecipeViewModel.idRecipe = idRecipe
        getRecipeViewModel.getRecipe()
    }
    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
                loadInfoRecipe()
            }else{
                Toast.makeText(this,"ocurrio un fallo: " + getRecipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun loadInfoRecipe() {

        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        val imageUri = RecipeProvider.recipeResponse.recipe.imageRecipeURL

        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = Glide.with(this@PrepareRecipeActivity)
                .asBitmap()
                .load(imageUri)
                .submit().get()

            withContext(Dispatchers.Main) {
                binding.ivRecipeImage.setImageBitmap(bitmap)
            }
        }

        val adapter = IngredientsShowRecipeAdapter()
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvIngredients.adapter = adapter
        for(position in 0..RecipeProvider.recipeResponse.ingredientList.count()-1){
            adapter.setItem(RecipeProvider.recipeResponse.ingredientList[position],RecipeProvider.recipeResponse.ingredientAmounList[position])
        }

        val adapterSteps = StepShowRecipeAdapter()
        binding.rvSteps.layoutManager = LinearLayoutManager(this)
        binding.rvSteps.adapter = adapterSteps
        for(position in 0..RecipeProvider.recipeResponse.stepList.count()-1){
            println("asdasdas   " + RecipeProvider.recipeResponse.stepList[position].instruction)
            adapterSteps.setItem(RecipeProvider.recipeResponse.stepList[position])
        }

    }
}