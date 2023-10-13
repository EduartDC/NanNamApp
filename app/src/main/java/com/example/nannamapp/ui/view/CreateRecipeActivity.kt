package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.IngredientProvider
import com.example.nannamapp.R

import com.example.nannamapp.databinding.ActivityCreateRecipeBinding
import com.example.nannamapp.ui.viewModel.CategoryViewModel
import com.example.nannamapp.ui.viewModel.IngredientViewModel


class CreateRecipeActivity : AppCompatActivity() {
    private  lateinit var  binding : ActivityCreateRecipeBinding
    private val categoryViewModel : CategoryViewModel by viewModels()
    private val ingredientViewModel : IngredientViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            categoryViewModel.onCreate()
            ingredientViewModel.onCreate()
        }catch (e: Exception){
            Log.e("tronó",e.cause.toString());
        }
        initCategoriesCB()
       configureAdapterIngredientsFinded()
        initIngredientsSearchBar()

    }

    private fun configureAdapterIngredientsFinded() {
        ingredientViewModel.ingredientModel.observe(this,Observer{
            Toast.makeText(this,"ya tiene ingredientes:  "+IngredientProvider.ingredients[1].ingredientname,Toast.LENGTH_LONG).show()
        })
        binding.ingerdientsFinded.layoutManager = LinearLayoutManager(this)
        adapterRVIngredientsFinded = IngredientFindedAdapater()
        binding.ingerdientsFinded.adapter = adapterRVIngredientsFinded
    }

    private lateinit var  categoryAdapter : ArrayAdapter<String>
    private  lateinit var categoryList : MutableList<String>
    private fun initIngredientsSearchBar() {//carga los eleleentos en el reciclyView de la barra de busqueda
        binding.SearchBar.addTextChangedListener(textWatcher)
    }
    val textWatcher = object : TextWatcher {// objeto usado para el listener del EditText
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Este método se llama cuando el texto cambia.
            val texto = s.toString() // Obtén el texto actual
            // Realiza acciones según el cambio en el texto
        }
        override fun afterTextChanged(s: Editable?) {
            setMatchIngredients()
        }
    }

    private lateinit var adapterRVIngredientsFinded : IngredientFindedAdapater
    private fun setMatchIngredients() {

        adapterRVIngredientsFinded.searchIngredientMatch(binding.SearchBar.text.toString())
    }




    private fun initCategoriesCB() {

        categoryViewModel.categoryModel.observe(this, Observer{
            categoryList = mutableListOf()
            for(i in 0..CategoryProvider.categories.size-1){
                categoryList.add(CategoryProvider.categories.get(i).categoryName)
            }
            categoryAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,categoryList)
            binding.cbCategories.adapter = categoryAdapter
        })
    }
}