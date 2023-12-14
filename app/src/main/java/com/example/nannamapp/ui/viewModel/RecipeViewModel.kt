package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.NewRecipeDomain
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.domain.EditRecipeUseCase
import com.example.nannamapp.domain.GetRecipeUseCase
import com.example.nannamapp.domain.PushNewRecipeUseCase
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    //VARIABLES USADAS PARA REGISTRAR RECETA
    var httpCodeCreateRecipe: Int = 0
    lateinit var newRecipeDomain: NewRecipeDomain
    lateinit var newRecipe: NewRecipePost

    val createRecipeUseCase: PushNewRecipeUseCase by lazy { PushNewRecipeUseCase(newRecipeDomain) }
    val editRecipeUseCase: EditRecipeUseCase by lazy { EditRecipeUseCase(newRecipe) }

    val recipeViewModel = MutableLiveData<Int>()

    fun postNewRecipe() {
        viewModelScope.launch {
            val result = createRecipeUseCase() // Llama al caso de uso
            //if (!result.isNullOrEmpty()) {
                recipeViewModel.postValue(result)
                httpCodeCreateRecipe = result
           // }
        }
    }
    //VARIABLES PARA RECUPERAR RECETA
    var httpCodegetRecipe : Int = 0
    var idRecipe : String = ""
    val getRecipeuseCase : GetRecipeUseCase by lazy { GetRecipeUseCase(idRecipe ) }
    val getRecipeViewModel = MutableLiveData<Int>()
    fun getRecipe(){
        viewModelScope.launch {
            var result = getRecipeuseCase()
            httpCodegetRecipe =result
            getRecipeViewModel.postValue(result)

        }
    }


    fun editRecipe() {
        viewModelScope.launch {
            val result = editRecipeUseCase() // Llama al caso de uso
            //if (!result.isNullOrEmpty()) {
            recipeViewModel.postValue(result)
            httpCodeCreateRecipe = result
            // }
        }
    }

}