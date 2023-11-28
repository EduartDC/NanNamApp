package com.example.nannamapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoadViewModel: ViewModel() {
    val showLoader = MutableLiveData<Boolean>()

fun setLoadTrue(){
showLoader.postValue(true);
}

    fun  setLoadFalse(){
        showLoader.postValue(false);
    }
}