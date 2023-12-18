package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.example.nannamapp.R
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.User
import com.example.nannamapp.databinding.ActivityModifyUserBinding
import com.example.nannamapp.ui.view.menu.StartMenu
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
        getUserViewModel.userModel.observe(this) { userModel ->
            if (userModel != null) {
                val editableText =
                    Editable.Factory.getInstance().newEditable(userModel.firstname)
                binding.nombre.text = editableText
                binding.apellido.text = Editable.Factory.getInstance().newEditable(userModel.lastname)
                binding.email.text = Editable.Factory.getInstance().newEditable(userModel.email)
            }
        }

        binding.saveChanges.setOnClickListener{

            val newPassword = binding.newPassword.text.toString()
            val firstName = binding.nombre.text.toString()
            val lastname = binding.apellido.text.toString()
            val user = User(idUser, firstName, lastname, "", "")
            if(binding.linearLayoutPassword.visibility==0){
                getUserViewModel.userModel.observe(this){
                        userModel ->
                    if (userModel != null) {
                        if(userModel.password != newPassword){
                            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }else{
                            user.password=newPassword
                        }
                    }
                }
            }
            getUserViewModel.updateUser(user)
            Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_SHORT).show()
            val i = Intent(this, StartMenu::class.java)
            startActivity(i)
        }
    }
    private fun getUser(idUser : String) {
        getUserViewModel.idUser = idUser
        getUserViewModel.getUser()
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