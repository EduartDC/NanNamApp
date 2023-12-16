package com.example.nannamapp.ui.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.namnam.data.model.Ingredient
import com.example.nannamapp.data.model.JsonResult
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.data.model.ReviewProvider
import com.example.nannamapp.databinding.ActivityShowRecipeBinding
import com.example.nannamapp.ui.view.menu.StartMenu
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.example.nannamapp.ui.viewModel.ReviewViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class ShowRecipeActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    lateinit var binding :ActivityShowRecipeBinding
    private val getRecipeViewModel : RecipeViewModel by viewModels()
    private val getReviewViewModel : ReviewViewModel by viewModels()

    private var idRecipeTest = "OWWHFXIPZQ"//id harcodeado, borrar cuando se haga la navegavilidad con CU buscar receta
    private var idUserTest = "1"//id hardcodeado, borrar cuando cris tenga el id del usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        idRecipeTest = intent.getStringExtra("key_idRecipe").toString()
        idUserTest = LoginProvider.login!!.idUser
        getRecipe(idRecipeTest)
        setListenerGetRecipe()
        listenerPrepareRecipe()
        listenerAddFavorites()
        listenerDeleteRecipe()
        listenerEditRecipe()
        listenerAddReview()

    }
    private fun getViewMenu(){
        val i = Intent(this, StartMenu::class.java)
        startActivity(i)
    }
    private fun validateOwnerRecipe() {
        if(!RecipeProvider.recipeResponse.recipe.user_idUser.equals(idUserTest))
        {
            binding.btnEditRecipe.visibility = View.GONE
            binding.btnDeleteRecipe.visibility = View.GONE
        }
    }

    //USADO POR ALGUIEN MAS
    private fun listenerEditRecipe() {
        binding.btnEditRecipe.setOnClickListener{
            val intent = Intent(this, EditRecipeActivity::class.java)

            // Agrega el String al Intent como un extra
            intent.putExtra("key_idRecipe", idRecipeTest)

            // Inicia la nueva Activity
            startActivity(intent)
        }
    }
    //USADO POR ALGUIEN MAS
    private fun listenerDeleteRecipe() {
        binding.btnDeleteRecipe.setOnClickListener{

        }
    }
    private fun saveIngredients() {
        val ingredientList = RecipeProvider.recipeResponse.ingredientList

        saveIngredientListToSharedPreferences(ingredientList)

        val intent = Intent(this, IngredientListActivity::class.java)

        intent.putParcelableArrayListExtra("ingredientList", ArrayList(ingredientList))

        startActivity(intent)
    }

    private fun saveIngredientListToSharedPreferences(ingredientList: List<Ingredient>) {
        val gson = Gson()
        val json = gson.toJson(ingredientList)
        val editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
        editor.putString("ingredientListKey", json)
        editor.apply()
    }

    // USADO POR ALGUIEN MÁS
    private fun listenerAddFavorites() {
        binding.btnSaveFavorites.setOnClickListener {
            saveIngredients()
        }
    }

    private fun listenerAddReview() {
        binding.btnAddReview.setOnClickListener(){
            if(binding.rbRating.rating > 0 && !binding.etReview.text.isNullOrBlank()){
                createReview()
            }else
            {
                Toast.makeText(this,"Para publicar una reseña es necesario introducir una reseña y una calificacion",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createReview() {
        var newReview : ReviewDomain  = ReviewDomain(
            "",
            binding.etReview.text.toString(),
            binding.rbRating.rating.toInt(),
            idUserTest,
            idRecipeTest
        )
        var bandNewReview : Boolean = true
        var idReviewEdited : String = ""
        if(ReviewProvider.reviews.count() != 0){
            for(item in ReviewProvider.reviews){
                if(idUserTest.equals(item.user_idUser)){
                    bandNewReview = false
                    idReviewEdited = item.idReview
                }
            }
        }

        if(bandNewReview)
        {
            binding.loadAnimation.visibility = View.VISIBLE
            saveNewRecipe(newReview)
        }
        else{
            binding.loadAnimation.visibility = View.VISIBLE
            editReview(newReview,idReviewEdited)

        }



    }

    private fun editReview(newReview: ReviewDomain, idReviewEdited : String) {
        println("METODO EDIT REVIEW")

        newReview.idReview = idReviewEdited
        getReviewViewModel.editReviewDomain = newReview
        getReviewViewModel.editNewReview()
        getReviewViewModel.editReviewViewModel.observe(this){
            binding.loadAnimation.visibility = View.GONE
            if(getReviewViewModel.httpCodeEditReview == 200){
                Toast.makeText(this,"reseña editada", Toast.LENGTH_SHORT).show()
                binding.etReview.text.clear()
                binding.rbRating.rating = 0f;
                loadReviews();
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("error de conexión: "+ getReviewViewModel.httpCodeEditReview)
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        getViewMenu()
                        dialog.dismiss()
                    }.show()

            }


        }
    }

    private fun saveNewRecipe(newReview: ReviewDomain) {
        println("salvando")
        getReviewViewModel.newReview = newReview
        getReviewViewModel.setNewReview()
        getReviewViewModel.setReviewViewModel.observe(this){
            if(getReviewViewModel.httpCodeSetReview == 200){
                Toast.makeText(this,"reseña registrada", Toast.LENGTH_SHORT).show()
                binding.etReview.text.clear()
                binding.rbRating.rating = 0f;
                loadReviews();
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("error de conexión: "+ getReviewViewModel.httpCodeSetReview)
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        getViewMenu()
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    //lamada a CU-Preparar receta
    private fun listenerPrepareRecipe() {
        binding.btnPrepareRecipe.setOnClickListener{
            val intent = Intent(this, PrepareRecipeActivity::class.java)

            // Agrega el String al Intent como un extra
            intent.putExtra("key_idRecipe", idRecipeTest)

            // Inicia la nueva Activity
            startActivity(intent)
        }
    }

    private fun setListenerGetRecipe() {
        getRecipeViewModel.getRecipeViewModel.observe(this){
            if(getRecipeViewModel.httpCodegetRecipe == 200){
             //   Toast.makeText(this,"Informacion recuperada " + RecipeProvider.recipeResponse.recipe.idMainIngredient,Toast.LENGTH_SHORT).show()
                if(loadReviews()){
                    loadInfoRecipe()
                    validateOwnerRecipe()
                    binding.loadAnimation.visibility = View.GONE
                }
            }else{
              //  Toast.makeText(this,"ocurrio un fallo: " + getRecipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this)
                builder.setMessage("error de conexión: "+ getRecipeViewModel.httpCodegetRecipe )
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        getViewMenu()
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    private fun loadReviews(): Boolean {
        var band : Boolean = true
        getReviewViewModel.idRecipe = idRecipeTest
        getReviewViewModel.getReviews()
        getReviewViewModel.getReviewViewModel.observe(this){
            if(getReviewViewModel.httpCodegetReview == 200){
                setReviewsConfig()
                if(ReviewProvider.reviews.size>=1){
                calculateAverageRating()}
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setMessage("error de conexión: "+ getRecipeViewModel.httpCodegetRecipe )
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        getViewMenu()
                        dialog.dismiss()
                    }.show()
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
            for(position in 0..ReviewProvider.reviews.count()-1){
                adapterReviews.setItem(ReviewProvider.reviews[position])
            }
        }
    }



    private fun loadInfoRecipe() {
        Picasso.get().load(RecipeProvider.recipeResponse.recipe.imageRecipeURL).into(binding.imgRecipe)
        binding.tvRecipeName.text = RecipeProvider.recipeResponse.recipe.recipeName
        println("URL: " + RecipeProvider.recipeResponse.recipe.imageRecipeURL)

        for (item in RecipeProvider.recipeResponse.ingredientList) {
            if (item.idIngredient == RecipeProvider.recipeResponse.recipe.idMainIngredient) {
                binding.tvMainIngredient.text = item.ingredientname
            }
        }

        binding.tvPortions.text = "" + RecipeProvider.recipeResponse.recipe.portion

        val adapter = IngredientsShowRecipeAdapter()
        binding.ingredientsFinded.layoutManager = LinearLayoutManager(this)
        binding.ingredientsFinded.adapter = adapter

        val ingredientList = RecipeProvider.recipeResponse.ingredientList

        for (position in 0 until ingredientList.size) {
            adapter.setItem(ingredientList[position], RecipeProvider.recipeResponse.ingredientAmounList[position])
        }

        val adapterSteps = StepShowRecipeAdapter()
        binding.rvIngredientSelected.layoutManager = LinearLayoutManager(this)
        binding.rvIngredientSelected.adapter = adapterSteps
        for (position in 0 until RecipeProvider.recipeResponse.stepList.size) {
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
        binding.tvAverage.text =  "promedio: " + average.toString() + "/5"


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