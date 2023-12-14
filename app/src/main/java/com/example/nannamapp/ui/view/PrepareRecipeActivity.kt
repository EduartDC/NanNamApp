package com.example.nannamapp.ui.view

import android.R
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivityPrepareRecipeBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PrepareRecipeActivity : AppCompatActivity() {

    lateinit var binding : ActivityPrepareRecipeBinding
    private val getRecipeViewModel : RecipeViewModel by viewModels()
    var idRecipe = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrepareRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent

        // Verifica si el intent contiene la clave "key_idRecipe"
        if (intent.hasExtra("key_idRecipe")) {
            // Obtiene el valor asociado con la clave "key_idRecipe"
            idRecipe = intent.getStringExtra("key_idRecipe").toString()
        }

        //idRecipe = "r1"
        try {
            getRecipe(idRecipe)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setListenerGetRecipe()

        val spinner: Spinner = findViewById(binding.cbPortions.id)
        val opciones = listOf("2", "4", "6", "8", "10", "12", "14", "16", "18", "20")

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
                adapter.portionCalculation(seleccion, RecipeProvider.recipeResponse.recipe.portion)
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
                binding.loadAnimation.visibility = View.GONE
            }else{
                //ventana de error
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Error de conexion")
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        // aqui deberia estar un metodo para cerrar la GUI
                        dialog.dismiss()
                    }.show()

            }
        }
    }
    private fun loadInfoRecipe() {

        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        val imageUri = RecipeProvider.recipeResponse.recipe.imageRecipeURL

        CoroutineScope(Dispatchers.IO).launch {
            try {


                val bitmap = Glide.with(this@PrepareRecipeActivity)
                    .asBitmap()
                    .load(imageUri)
                    .submit().get()

                withContext(Dispatchers.Main) {
                    binding.ivRecipeImage.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
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

        var sort = RecipeProvider.recipeResponse.stepList.sortedBy { it.step }
        for(position in 0..sort.count()-1){
            println("asdasdas   " + RecipeProvider.recipeResponse.stepList[position].instruction)
            adapterSteps.setItem(sort[position])
        }

    }
}