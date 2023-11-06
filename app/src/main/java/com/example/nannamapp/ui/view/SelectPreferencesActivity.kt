package com.example.nannamapp.ui.view

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nannamapp.R
import com.example.nannamapp.data.model.UserPreferenceProvider
import com.example.nannamapp.databinding.ActivitySelectPreferencesBinding
import com.example.nannamapp.ui.viewModel.UserPreferenceViewModel

class SelectPreferencesActivity : AppCompatActivity() {
    lateinit var  binding : ActivitySelectPreferencesBinding
    private val preferencesViewModel : UserPreferenceViewModel by viewModels()
    //usuario hardcodedo
    private var idUser :String = "123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserPreferences()
    }


    private fun getUserPreferences() {
        preferencesViewModel.idUser =idUser
        preferencesViewModel.getUserPreferences()
        preferencesViewModel.getUserPreferenceViewModel.observe(this){
            if(preferencesViewModel.httpCodegetUserPreference == 200){
                //Toast.makeText(this,"si jaló: " + UserPreferenceProvider.prefererence.userpreferences.size,Toast.LENGTH_SHORT).show()
                loadInformation()
            }else{
                Toast.makeText(this,"Hubo un pedo",Toast.LENGTH_SHORT).show()
            }
        }
    }


    //carga la informacion recuperada en la pantalla
    private fun loadInformation() {
        val preferenceAdapter = UserPreferenceAdapter()
        binding.rvPreferences.layoutManager = LinearLayoutManager(this)
        binding.rvPreferences.adapter = preferenceAdapter
        for(item in UserPreferenceProvider.prefererence.categories){
            println("CARGANDO PREFERENCIAS")
            preferenceAdapter.setItem(item)
        }
        setListenerBtn()
    }

    private fun setListenerBtn() {
        binding.btnSavePreferences.setOnClickListener(){

            /*val builder = AlertDialog.Builder(this)
            builder.setMessage("Cambios guardados")
                .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                    // aqui deberia estar un metodo para cerrar la GUI
                    dialog.dismiss()
                }.show()*/
        }
    }


}