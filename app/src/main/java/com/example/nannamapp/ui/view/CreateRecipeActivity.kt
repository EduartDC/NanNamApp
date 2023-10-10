package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels

import com.example.nannamapp.databinding.ActivityCreateRecipeBinding
import com.example.nannamapp.ui.viewModel.CategoryViewModel


class CreateRecipeActivity : AppCompatActivity() {
    private  lateinit var  binding : ActivityCreateRecipeBinding
    private val categoryViewModel : CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            categoryViewModel.onCreate()
        }catch (e: Exception){
            Toast.makeText(this, e.stackTraceToString(), Toast.LENGTH_LONG).show();
            Log.e("tronó",e.cause.toString());
        }
        initCategoriesCB()

    }

    private fun initCategoriesCB() {

    }
}