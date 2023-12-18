package com.example.nannamapp.ui.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nannamapp.data.model.LoginProvider
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivityConsultCookbookBinding
import com.example.nannamapp.ui.viewModel.CookBookViewModel


class ConsultCookbookActivity : AppCompatActivity(), CookBookAdapter.OnCardClickListener{
    private lateinit var binding: ActivityConsultCookbookBinding
    private val cookbookViewModel: CookBookViewModel by viewModels()
    private lateinit var recipeAdapter: CookBookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultCookbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.emptyView.visibility = View.GONE

        //obtener id del usuario
        var idUser = LoginProvider.login!!.idUser
        cookbookViewModel.idUser = idUser
        // Inicia la carga de datos
        cookbookViewModel.onCreate()

        // Configura el RecyclerView
        //binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(com.example.nannamapp.R.id.recyclerview)
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        // Inicializa el adaptador
        recipeAdapter = CookBookAdapter(this)
        binding.recyclerview.adapter = recipeAdapter

        // Observa los cambios en la lista de recetas
        cookbookViewModel.cookBookModel.observe(this, {
                if(cookbookViewModel.httpCodeRecipe == 200) {
                    recipeAdapter.setData(RecipeProvider.cookBook)
                    binding.loadAnimation.visibility = View.GONE
                }
                else if(cookbookViewModel.httpCodeRecipe == 404){
                    binding.emptyView.visibility = View.VISIBLE
                    binding.loadAnimation.visibility = View.GONE
                }
                else{
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Error de conexion")
                        .setPositiveButton("Cerrar") { dialog: DialogInterface, which: Int ->
                            // aqui deberia estar un metodo para cerrar la GUI
                            dialog.dismiss()
                        }.show()
                }
        })

    }

    override fun onCardClick(position: Int) {

        val idRecipe = RecipeProvider.cookBook[position].idRecipe
        // Crea un Intent
        val intent = Intent(this, ShowRecipeActivity::class.java)

        // Agrega el String al Intent como un extra
        intent.putExtra("key_idRecipe", idRecipe)

        // Inicia la nueva Activity
        startActivity(intent)
    }

}