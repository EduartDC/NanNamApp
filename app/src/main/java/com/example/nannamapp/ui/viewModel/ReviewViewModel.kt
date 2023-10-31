package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.domain.GetRecipeUseCase
import com.example.nannamapp.domain.GetReviewsUseCase
import com.example.nannamapp.domain.PushNewRecipeUseCase
import com.example.nannamapp.domain.SetReviewUseCase
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel(){

    //VARIABLES PARA RECUPERAR RECETA
    var httpCodegetReview : Int = 0
    var idRecipe : String = ""
    val getReviewUseCase : GetReviewsUseCase by lazy { GetReviewsUseCase(idRecipe ) }
    val getReviewViewModel = MutableLiveData<Int>()
    fun getReviews(){
        viewModelScope.launch {
            var result = getReviewUseCase()
            httpCodegetReview = result
            getReviewViewModel.postValue(result)

        }
    }

    var httpCodeSetReview: Int = 0
    lateinit var newReview: ReviewDomain
    val setReviewUseCase: SetReviewUseCase by lazy { SetReviewUseCase(newReview) }
    val setReviewViewModel = MutableLiveData<Int>()

    fun setNewReview() {
        viewModelScope.launch {
            val result = setReviewUseCase() // Llama al caso de uso
            //if (!result.isNullOrEmpty()) {
            setReviewViewModel.postValue(result)
            httpCodeSetReview = result
            //}
        }
    }
}