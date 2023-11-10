package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nannamapp.R
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.databinding.ActivityLoginBinding
import com.example.nannamapp.ui.view.menu.StartMenu
import com.example.nannamapp.ui.viewModel.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var loginViewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerText.setOnClickListener{
            goToRegister()
        }

        val loginButton = binding.loginButton
        loginButton.setOnClickListener{
            val emailEditText = binding.username.text.toString()
            val passwordEditText = binding.password.text.toString()

            if(emailEditText.isEmpty() || passwordEditText.isEmpty()){
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                var result = loginViewModel.onCreate(Login(emailEditText, passwordEditText))

                if(result != null){
                    authenticate()
                }else{
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToRegister() {
        val i = Intent(this, Register::class.java)
        startActivity(i)
    }

    private fun authenticate(){
        loginViewModel.loginModel.observe(this) {jsonResult ->
            if (jsonResult != null){
                Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                goToMenu()
            }else {
                Toast.makeText(this, "Hubo un error al intentar iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMenu() {
        val i = Intent(this, StartMenu::class.java)
        startActivity(i)
    }

    override fun onBackPressed() {
    }

}