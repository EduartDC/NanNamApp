package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityConsultCookbookBinding
import com.example.nannamapp.databinding.ActivityCreateRecipeBinding
import com.example.nannamapp.ui.viewModel.CategoryViewModel
import com.example.nannamapp.ui.viewModel.CookBookViewModel

class ConsultCookbook : AppCompatActivity() {
    private  lateinit var  binding : ActivityConsultCookbookBinding
    private val cookbookViewModel : CookBookViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult_cookbook)
    }
}