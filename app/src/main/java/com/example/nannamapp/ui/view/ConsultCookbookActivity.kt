package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nannamapp.R
import com.example.nannamapp.databinding.ActivityConsultCookbookBinding
import com.example.nannamapp.ui.viewModel.CookBookViewModel

class ConsultCookbookActivity : AppCompatActivity() {
    private  lateinit var  binding : ActivityConsultCookbookBinding
    private val cookbookViewModel : CookBookViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultCookbookBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_consult_cookbook)
        cookbookViewModel.onCreate()
    }
}