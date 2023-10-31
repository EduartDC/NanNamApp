package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.data.model.ReviewProvider
import com.example.nannamapp.databinding.ActivityShowRecipeBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.example.nannamapp.ui.viewModel.ReviewViewModel
import com.squareup.picasso.Picasso

class ShowRecipeActivity : AppCompatActivity() {
    lateinit var binding :ActivityShowRecipeBinding
    private val getRecipeViewModel : RecipeViewModel by viewModels()
    private val getReviewViewModel : ReviewViewModel by viewModels()
    private var idRecipeTest = "RIJT6I55VQ"//id harcodeado, borrar cuando se haga la navegavilidad con CU buscar receta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //varibales para que me pase el id de la receta
        val intent =intent
        getRecipe(idRecipeTest)
        setListenerGetRecipe()
        listenerPrepareRecipe()
        listenerAddFavorites()
        listenerDeleteRecipe()
        listenerEditRecipe()
        listenerAddReview()
    }

    //USADO POR ALGUIEN MAS
    private fun listenerEditRecipe() {
        binding.btnEditRecipe.setOnClickListener{

        }
    }
    //USADO POR ALGUIEN MAS
    private fun listenerDeleteRecipe() {
        binding.btnDeleteRecipe.setOnClickListener{

        }
    }
    //USADO POR ALGUIEN MAS
    private fun listenerAddFavorites() {
        binding.btnSaveFavorites.setOnClickListener{

        }
    }

    private fun listenerAddReview() {
        binding.btnAddReview.setOnClickListener(){
            if(binding.rbRating.rating > 0 && !binding.etReview.text.isNullOrBlank()){
                createReview()
            }else
            {
                Toast.makeText(this,"escoge algun campo",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createReview() {
        var newReview : ReviewDomain  = ReviewDomain(
            "",
            binding.etReview.text.toString(),
            binding.rbRating.rating.toInt(),
            "123",
            idRecipeTest
        )
        getReviewViewModel.newReview = newReview
        getReviewViewModel.setNewReview()
        getReviewViewModel.setReviewViewModel.observe(this){
            if(getReviewViewModel.httpCodeSetReview == 200){
                Toast.makeText(this,"reseña registrada", Toast.LENGTH_SHORT).show()
                binding.etReview.text.clear()
                binding.rbRating.rating = 0f;
                loadReviews();
            }
            else
                Toast.makeText(this,"fallo al registrar", Toast.LENGTH_SHORT).show()

        }


    }

    //lamada a CU-Preparar receta
    private fun listenerPrepareRecipe() {
        binding.btnPrepareRecipe.setOnClickListener{
            val intent = Intent(this, PrepareRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
                Toast.makeText(this,"Informacion recuperada " + RecipeProvider.recipeResponse.recipe.idMainIngredient,Toast.LENGTH_SHORT).show()
                if(loadReviews()){
                    loadInfoRecipe()

                }



            }else{
                Toast.makeText(this,"ocurrio un fallo: " + getRecipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun loadReviews(): Boolean {
        var band : Boolean = true
        getReviewViewModel.idRecipe = idRecipeTest
        getReviewViewModel.getReviews()
        getReviewViewModel.getReviewViewModel.observe(this){
            if(getReviewViewModel.httpCodegetReview == 200){
                //Toast.makeText(this,"BIEN: " ,Toast.LENGTH_SHORT).show()
                setReviewsConfig()
                calculateAverageRating()
            }else{
                Toast.makeText(this,"Fallo al recuperar reseñas: ",Toast.LENGTH_SHORT).show()

            }
        }
        return band
    }

    //confiura el adapter para las reseñas
    private fun setReviewsConfig() {
        if(!ReviewProvider.reviews.isNullOrEmpty()){
            val adapterReviews = ReviewAdapter()
            binding.rvReviews.layoutManager = LinearLayoutManager(this)
            binding.rvReviews.adapter = adapterReviews
            println("taano de reviews " + ReviewProvider.reviews.count())
            for(position in 0..ReviewProvider.reviews.count()-1){
                adapterReviews.setItem(ReviewProvider.reviews[position])
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
        Picasso.get().load(RecipeProvider.recipeResponse.recipe.imageRecipeURL).into(binding.imgRecipe);
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
            adapterSteps.setItem(RecipeProvider.recipeResponse.stepList[position])
        }

        loadNutritionalData()

    }

    private fun calculateAverageRating() {
        var sumAverage = 0
        for (position in 0..ReviewProvider.reviews.count()-1){
            println("item: "+ ReviewProvider.reviews[position].rate)
            sumAverage += ReviewProvider.reviews[position].rate
        }
        val average : Float = sumAverage.toFloat() / ReviewProvider.reviews.count()
        binding.tvAverage.text =  ""+binding.tvAverage.text + average.toString() + "/5"

    }

    private fun loadNutritionalData() {
        var calories: Float = 0f
        var fat: Float = 0f
        var protein: Float = 0f
        var carbohidrates : Float = 0f

        for (position in 0..RecipeProvider.recipeResponse.nutritionalDataList.count()-1){
            if(RecipeProvider.recipeResponse.ingredientList[position].measure.equals("gr")|| RecipeProvider.recipeResponse.ingredientList[position].measure.equals("ml")){
                calories += (RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].calories.toFloat())/100
                fat += (RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].fat.toFloat())/100
                protein += (RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].protein.toFloat())/100
                carbohidrates += (RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].carbohydrates.toFloat())/100
            }else{
                calories += RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].calories.toFloat()
                fat += RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].calories.toFloat()
                protein += RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].protein.toFloat()
                carbohidrates += RecipeProvider.recipeResponse.ingredientAmounList[position].amount.toFloat() * RecipeProvider.recipeResponse.nutritionalDataList[position].carbohydrates.toFloat()

            }
        }

        binding.tvCalories.text =  ""+binding.tvCalories.text + calories.toString() + "gr"
        binding.tvFat.text = ""+binding.tvFat.text+fat.toString()+ "gr"
        binding.tvProtein.text =""+binding.tvProtein.text+ protein.toString()+ "gr"
        binding.tvCarbohydrates.text =""+binding.tvCarbohydrates.text+ carbohidrates.toString()+ "gr"
    }

    private fun getRecipe(idRecipe : String) {
        getRecipeViewModel.idRecipe = idRecipe
        getRecipeViewModel.getRecipe()
    }
}