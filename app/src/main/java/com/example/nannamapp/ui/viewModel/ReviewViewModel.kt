package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.ReviewDomain
import com.example.nannamapp.domain.EditReviewUseCase
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
    lateinit var  setReviewUseCase: SetReviewUseCase
    val setReviewViewModel = MutableLiveData<Int>()

    fun setNewReview() {
        viewModelScope.launch {
            setReviewUseCase = SetReviewUseCase()
            val result = setReviewUseCase(newReview) // Llama al caso de uso
            //if (!result.isNullOrEmpty()) {
            setReviewViewModel.postValue(result)
            httpCodeSetReview = result
            //}
        }
    }

    var httpCodeEditReview: Int = 0
    lateinit var editReviewDomain: ReviewDomain
    lateinit var  editReviewUseCase: EditReviewUseCase
    val editReviewViewModel = MutableLiveData<Int>()

    fun editNewReview() {
        viewModelScope.launch {
            editReviewUseCase = EditReviewUseCase()
            val result = editReviewUseCase(editReviewDomain) // Llama al caso de uso
            //if (!result.isNullOrEmpty()) {
            editReviewViewModel.postValue(result)
            httpCodeEditReview = result
            //}
        }
    }
}