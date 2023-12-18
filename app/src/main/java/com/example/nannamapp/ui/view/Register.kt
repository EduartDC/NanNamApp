package com.example.nannamapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.MalformedJsonException
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.nannamapp.R
import com.example.nannamapp.data.model.Login
import com.example.nannamapp.data.model.User
import com.example.nannamapp.databinding.ActivityRegisterBinding
import com.example.nannamapp.ui.view.menu.StartMenu
import com.example.nannamapp.ui.viewModel.LoginViewModel
import com.example.nannamapp.ui.viewModel.RegisterViewModel
import java.util.regex.Pattern

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private var registerViewModel = RegisterViewModel()
    private var loginViewModel = LoginViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerButton.setOnClickListener{
            register()
        }

        binding.loginText.setOnClickListener{
            if (!hasData()){
                goToLogin()
            }else{
                showConfirmationDialog()
            }
        }
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun register() {
        try {
            val firstName = binding.firstname.text.toString()
            val lastname = binding.lastname.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val passwordConfirmed = binding.passwordConfirmed.text.toString()

            if (hasData()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (!password.equals(passwordConfirmed)) {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                } else {
                    if (isValidEmail(email)) {
                        val user = User("0", firstName, lastname, email, password)
                        var result = registerViewModel.onCreate(user)

                        if (result != null) {
                            Toast.makeText(this, "¡Ahora eres chef!", Toast.LENGTH_SHORT).show()
                            goToMenu()
                        } else {
                            Toast.makeText(this, "Error al registrarse", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: MalformedJsonException) {
            // Manejo de la excepción MalformedJsonException
            e.printStackTrace() // o utiliza tu propio método de registro de errores

            // Puedes agregar aquí el código para manejar la excepción según tus necesidades.
            // Por ejemplo, puedes mostrar un mensaje de error al usuario o realizar alguna acción específica.
        }
    }



    private fun goToMenu() {
        val i = Intent(this, StartMenu::class.java)
        startActivity(i)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

    private fun hasData(): Boolean {
        if (binding.firstname.text.toString().isEmpty() || binding.lastname.text.toString().isEmpty() ||
            binding.email.text.toString().isEmpty() || binding.password.text.toString().isEmpty() ||
            binding.passwordConfirmed.text.toString().isEmpty() || binding.password.hint.toString().equals("Contraseña") ||
            binding.passwordConfirmed.hint.toString().equals("Confirmar contraseña")){
            return false
        }
        return true
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Cancelar registro?")
        builder.setMessage("Tienes datos ingresados. Al regresar se perderán. ¿Deseas continuar?")

        builder.setPositiveButton("Sí") { dialog, _ ->
            goToLogin()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}