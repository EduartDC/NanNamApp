package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.GetRecipeResponse
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.domain.GetRecipeUseCase
import com.example.nannamapp.domain.PushNewRecipeUseCase
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    //VARIABLES USADAS PARA REGISTRAR RECETA
    var httpCodeCreateRecipe: Int = 0
    lateinit var newRecipe: NewRecipePost
    val createRecipeUseCase: PushNewRecipeUseCase by lazy { PushNewRecipeUseCase(newRecipe) }
    val recipeViewModel = MutableLiveData<Int>()

    fun postNewRecipe() {
        viewModelScope.launch {
          // createRecipeUseCase.newRecipe = newRecipe // Establece newRecipe en el caso de uso
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

}