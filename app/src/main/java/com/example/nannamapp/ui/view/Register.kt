package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import com.example.nannamapp.R
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.data.model.User
import com.example.nannamapp.databinding.ActivityRegisterBinding
import com.example.nannamapp.ui.view.menu.StartMenu
import com.example.nannamapp.ui.viewModel.LoginViewModel
import com.example.nannamapp.ui.viewModel.RegisterViewModel

class Register : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var binding: ActivityRegisterBinding

    private var registerViewModel = RegisterViewModel()
    private var loginViewModel = LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerButton.setOnClickListener{
            val firstName = binding.firstname.text.toString()
            val lastname = binding.lastname.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val passwordConfirmed = binding.passwordConfirmed.text.toString()

            if (firstName.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmed.isEmpty()){
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                if(!password.equals(passwordConfirmed)){
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }else{
                    val user =  User("0", firstName, lastname, email, password)
                    var result = registerViewModel.onCreate(user)

                    if (result != null){

                        var result = loginViewModel.onCreate(Login(email, password))
                        //goToMenu()
                        var message = "¡Ahora eres chef!"
                        handler.postDelayed({
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        },500)
                        goToMenu()
                    }else {
                        Toast.makeText(this, "Error al registrarse", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val loginText = findViewById<TextView>(R.id.loginText)
        loginText.setOnClickListener{
            //Agregar que no se regrese si tiene datos ya introducidos en los campos
            goToLogin()
        }
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    /*private fun register(){
        registerViewModel.registerModel.observe(this) {result ->
            if (result != null){
                Toast.makeText(this, "¡Ya puedes iniciar sesión y ser chef!", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(this, "Error al registrarse", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    private fun restart() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToMenu() {
        val i = Intent(this, StartMenu::class.java)
        startActivity(i)
    }

}