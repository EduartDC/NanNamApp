package com.example.nannamapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nannamapp.R
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.User
import com.example.nannamapp.databinding.ActivityModifyUserBinding
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.example.nannamapp.ui.viewModel.UserViewModel

class ModifyUser : AppCompatActivity() {
    private lateinit var binding: ActivityModifyUserBinding
    private val getUserViewModel : UserViewModel by viewModels()
    private var idUser = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        idUser = LoginProvider.login!!.idUser
        super.onCreate(savedInstanceState)
        binding = ActivityModifyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUser(idUser)


    }
    private fun getUser(idUser : String) {
        getUserViewModel.idUser = idUser
        getUserViewModel.getRecipe()
    }

    fun showLinearLayout_password(view: View) {
        binding.linearLayoutPassword.visibility = View.VISIBLE
        binding.modifyPassword.visibility = View.GONE
    }

    fun hideLinearLayout_password(view: View) {
        binding.linearLayoutPassword.visibility = View.GONE
        binding.modifyPassword.visibility = View.VISIBLE
    }
}