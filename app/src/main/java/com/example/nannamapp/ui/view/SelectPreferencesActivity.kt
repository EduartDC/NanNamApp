package com.example.nannamapp.ui.view

import android.content.DialogInterface
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nannamapp.R
import com.example.nannamapp.data.model.GetPreferenceResponse
import com.example.nannamapp.data.model.SetPreferenceResponse
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
        setListenerObserver()
    }

    private fun setListenerObserver() {
        preferencesViewModel.setUserPreferenceViewModel.observe(this){
            binding.loadAnimation.visibility = View.GONE
            if(preferencesViewModel.httpCodeSetUserPreference == 200){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Cambios guardados")
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        // aqui deberia estar un metodo para cerrar la GUI
                        dialog.dismiss()
                    }.show()
            }else
            {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("ocurrio un error"+ preferencesViewModel.httpCodeSetUserPreference)
                    .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                        // aqui deberia estar un metodo para cerrar la GUI
                        dialog.dismiss()
                    }.show()
            }
        }
    }


    private fun getUserPreferences() {
        preferencesViewModel.idUser =idUser
        preferencesViewModel.getUserPreferences()
        preferencesViewModel.getUserPreferenceViewModel.observe(this){
            binding.loadAnimation.visibility = View.GONE
            if(preferencesViewModel.httpCodegetUserPreference == 200){
                Toast.makeText(this,"si jaló: " + UserPreferenceProvider.prefererence.userpreferences.size,Toast.LENGTH_SHORT).show()
                loadInformation()
            }else{
                Toast.makeText(this,"Hubo un problema",Toast.LENGTH_SHORT).show()
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
        var userPreferenceList : MutableList<String> = mutableListOf()
        binding.loadAnimation.visibility = View.VISIBLE
        val preferenceSelectedAdapter = binding.rvPreferences.adapter as UserPreferenceAdapter
            for(position in 0 until preferenceSelectedAdapter.itemCount){
                val viewHolder = binding.rvPreferences.findViewHolderForAdapterPosition(position) as? UserPreferenceAdapter.preferenceAdapterViewHolder
                if(viewHolder != null){
                    if(viewHolder.rbCategory.isChecked){
                        for(cb in UserPreferenceProvider.prefererence.categories){
                            if(viewHolder.rbCategory.text.equals(cb.categoryName)){
                                userPreferenceList.add(cb.idCategory)
                            }
                        }
                    }
                }

            }
            var setPreferenceResponseTemp : SetPreferenceResponse = SetPreferenceResponse(
                idUser,
                userPreferenceList
            )

            preferencesViewModel.setPreferenceResponse = setPreferenceResponseTemp

            preferencesViewModel.setNewUserPreference()


        }
    }


}