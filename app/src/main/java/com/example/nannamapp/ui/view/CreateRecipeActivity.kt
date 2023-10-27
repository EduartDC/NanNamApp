package com.example.nannamapp.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.Ingredient
import com.example.namnam.data.model.IngredientProvider
import com.example.nannamapp.R

import com.example.nannamapp.databinding.ActivityCreateRecipeBinding
import com.example.nannamapp.ui.viewModel.CategoryViewModel
import com.example.nannamapp.ui.viewModel.IngredientViewModel
import com.example.nannamapp.util.IngredientSelectedAdapter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


class CreateRecipeActivity : AppCompatActivity() {

    public lateinit var binding: ActivityCreateRecipeBinding
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val ingredientViewModel: IngredientViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            categoryViewModel.onCreate()
            ingredientViewModel.onCreate()
        } catch (e: Exception) {
            Log.e("tronó", e.cause.toString());
        }
        setupListenerCamera()
        initCategoriesCB()
        configureAdapterIngredientsFinded()
        initIngredientsSearchBar()
        configureRecycleViewCookingSteps()
        setListenerBtnSave()
    }

    private fun setListenerBtnSave() {
        binding.btnSaveRecipe.setOnClickListener{
            var ingredientSelectedList= adapterRVIngredientsFinded.getIngredientSelectedList()
            var x =0
           if(!binding.recipeName.text.isNullOrBlank()){
                if(ingredientSelectedList.getContextIngredientSelectedAdapter().ingredientSelected.size != 0){
                    if(validateMeasureSelected())
                        if(validateSteps())
                            Toast.makeText(this,"LISTO PARA GUARDAR",Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this,"ingresa pasos",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this,"ingresa las unidades",Toast.LENGTH_SHORT).show()
                }else
                    Toast.makeText(this,"Selecciona almenos un ingrediente",Toast.LENGTH_SHORT).show()
           }else
               Toast.makeText(this,"Nombre obligatorio",Toast.LENGTH_SHORT).show()

        }
    }

    fun validateMeasureSelected (): Boolean{
        var band :Boolean = true
        val adapter = binding.rvIngredientSelected.adapter as IngredientSelectedAdapter

        for (position in 0 until adapter.itemCount) {
            val viewHolder = binding.rvIngredientSelected.findViewHolderForAdapterPosition(position) as? IngredientSelectedAdapter.IngredientSelectedViewHolder

            // Verifica si el ViewHolder es nulo
            if (viewHolder != null) {
                val editText = viewHolder.etMeasure
                val text = editText.text.toString()
                if (text.isEmpty()) {
                    // Si algún EditText está vacío, retorna false
                    return false
                }
            }
        }

        // Si todos los EditText tienen contenido, retorna true
        return true
    }

    fun validateSteps (): Boolean{
        var band :Boolean = true
        val adapter = binding.rvCookingSteps.adapter as CookingStepAdapter
        if (adapter.itemCount == 0)
                return false
        return true
    }




    private fun configureAdapterIngredientsFinded() {
        binding.ingerdientsFinded.layoutManager = LinearLayoutManager(this)
        adapterRVIngredientsFinded = IngredientFindedAdapater()
        adapterRVIngredientsFinded.setIngredientSelectedAdapter(binding,this)
        binding.ingerdientsFinded.adapter = adapterRVIngredientsFinded

    }

    private lateinit var categoryAdapter: ArrayAdapter<String>
    private lateinit var categoryList: MutableList<String>
    private fun initIngredientsSearchBar() {//carga los eleleentos en el reciclyView de la barra de busqueda
        binding.SearchBar.addTextChangedListener(textWatcher)
    }

    // objeto usado para el listener del EditText
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Este método se llama cuando el texto cambia.
            val texto = s.toString() // Obtén el texto actual
        }

        override fun afterTextChanged(s: Editable?) {
            setMatchIngredients()
        }
    }

    private lateinit var adapterRVIngredientsFinded: IngredientFindedAdapater
    private fun setMatchIngredients() {

        adapterRVIngredientsFinded.searchIngredientMatch(
            binding.SearchBar.text.toString(),
            binding,
            this
        )
    }


    private fun initCategoriesCB() {

        categoryViewModel.categoryModel.observe(this, Observer {
            categoryList = mutableListOf()
            for (i in 0..CategoryProvider.categories.size - 1) {
                categoryList.add(CategoryProvider.categories.get(i).categoryName)
            }
            categoryAdapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categoryList
            )
            binding.cbCategories.adapter = categoryAdapter
            binding.cbCategories.setSelection(-1)//se supone que deberia de quitar el valor por defecto pero no lo hace
        })
    }

    private lateinit var cookingStepsAdapter: CookingStepAdapter

    @SuppressLint("SuspiciousIndentation")
    private fun configureRecycleViewCookingSteps() {
        binding.rvCookingSteps.layoutManager = LinearLayoutManager(this)
        cookingStepsAdapter = CookingStepAdapter()
        cookingStepsAdapter.setTouchHelper(itemTouchHelper)
        binding.rvCookingSteps.adapter = cookingStepsAdapter

        binding.btnAddStep.setOnClickListener {
            if (!binding.etStepText.text.isNullOrBlank()) {
                cookingStepsAdapter.addStep(binding.etStepText.text.toString())
                Toast.makeText(this, "Paso Agregado", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Campo Vacio", Toast.LENGTH_SHORT).show()

            binding.etStepText.text.clear()
        }
        itemTouchHelper.attachToRecyclerView(binding.rvCookingSteps)//asociar la funcionalidad drag and drop al recycleView
    }

    //variable para la propiedad de drag and drop
    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        0
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // Obtén las posiciones de los elementos que deseas reorganizar
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition

            // Realiza la reorganización de los elementos en tu lista de datos (adapter)
            cookingStepsAdapter.moveItem(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Manejar el deslizamiento (si es necesario)
        }
    })

    //CODIGO PARA LA CONFIGURACION DE LA CAMARA
    companion object {
        private const val PERMISSION_CODE_CAMERA = 998
        private const val PERMISSION_CODE_GALLERY = 999
        private const val REQUEST_CODE_CAMERA = 1000
        private const val REQUEST_CODE_GALLERY = 1001
    }

    private lateinit var filePhoto: File
    private fun setupListenerCamera() {
        binding.btnTakePhoto.setOnClickListener {
            checkUserGrantedCameraPermission()
        }
    }

    private fun checkUserGrantedCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            val permission = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permission, PERMISSION_CODE_CAMERA)
        } else {
            openCamera()
        }
    }

    //funciona para la galeria pero no se ha implementado el boton
    private fun checkUserGrantedGalleryPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, PERMISSION_CODE_GALLERY)
        } else {
            openImageGallery()
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // File name
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val cameraPhotoFileName = "Photo_${timestamp}.jpg"

        filePhoto = getPhotoFile(cameraPhotoFileName)
        val providerFile = FileProvider.getUriForFile(this, "com.example.NamNamApp.fileprovider", filePhoto)

        // Start intent
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA)
    }

    private fun getPhotoFile(cameraPhotoFileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(cameraPhotoFileName, ".jpg", directoryStorage)
    }

    private fun openImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            PERMISSION_CODE_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImageGallery()
                } else {
                    Toast.makeText(this, "Gallery permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            val imageUri = Uri.parse(filePhoto.absolutePath)
            binding.imgRecipe.setImageURI(imageUri)
        } else if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            binding.imgRecipe.setImageURI(data?.data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}