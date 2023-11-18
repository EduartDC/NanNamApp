package com.example.nannamapp.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.namnam.data.model.Category
import com.example.namnam.data.model.CategoryProvider
import com.example.namnam.data.model.CookinginstructionDomain
import com.example.namnam.data.model.IngredientProvider
import com.example.namnam.data.model.RecipeDomain
import com.example.nannamapp.data.model.NewRecipePost
import com.example.nannamapp.data.model.RecipeHasIngredient
import com.example.nannamapp.data.model.RecipeProvider
import com.example.nannamapp.databinding.ActivityEditRecipeBinding
import com.example.nannamapp.ui.viewModel.CategoryViewModel
import com.example.nannamapp.ui.viewModel.IngredientViewModel
import com.example.nannamapp.ui.viewModel.RecipeViewModel
import com.example.nannamapp.util.EditIngredientSelectedAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class EditRecipeActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditRecipeBinding
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val ingredientViewModel: IngredientViewModel by viewModels()
    private val recipeViewModel : RecipeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var idRecipe = "RIJT6I55VQ"
        getRecipe(idRecipe)
        if(recipeViewModel.httpCodegetRecipe == 200) {
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
            setListenerGetRecipe()
        }
        else{
            //mandar a otra ventana o algo
            Toast.makeText(this,"ocurrio un fallo: " + recipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()
        }

    }
    private fun getRecipe(idRecipe : String) {
        recipeViewModel.idRecipe = idRecipe
        recipeViewModel.getRecipe()
    }
    private fun setListenerGetRecipe() {
        recipeViewModel.getRecipeViewModel.observe(this){
            if(recipeViewModel.httpCodegetRecipe == 200){
                loadInfoRecipe()
            }else{
                Toast.makeText(this,"ocurrio un fallo: " + recipeViewModel.httpCodegetRecipe,Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun loadInfoRecipe() {
        val adapterIngredientSelected = binding.rvIngredientSelected.adapter as EditIngredientSelectedAdapter
        val adaprteSteps = binding.rvCookingSteps.adapter as CookingStepAdapter

        val name:String = RecipeProvider.recipeResponse.recipe.recipeName
        binding.recipeName.setText(name)
        val imageUri = RecipeProvider.recipeResponse.recipe.imageRecipeURL
        binding.etPortions.setText(RecipeProvider.recipeResponse.recipe.portion.toString())


        try {
            CoroutineScope(Dispatchers.IO).launch {
                val bitmap = Glide.with(this@EditRecipeActivity)
                    .asBitmap()
                    .load(imageUri)
                    .submit().get()

                withContext(Dispatchers.Main) {
                    binding.imgRecipe.setImageBitmap(bitmap)
                }
            }
        } catch (e: Exception) {
            Log.e("EditRecipeActivity", e.message.toString())
        }
        binding.cbCategories.setSelection(CategoryProvider.categories.indexOf(RecipeProvider.recipeResponse.category))
        var mainIngredient = ""
        for(position in 0 .. RecipeProvider.recipeResponse.ingredientList.count()-1){
            val ingredient = RecipeProvider.recipeResponse.ingredientList[position]
            val measure = RecipeProvider.recipeResponse.ingredientAmounList[position]
            adapterIngredientSelected.amountIngredientTest = measure.amount
            adapterIngredientSelected.addIngredientSelected(ingredient)
            adapterRVIngredientsFinded.mainIngerdientsList.add(ingredient.ingredientname)

            val idmainIngredient = RecipeProvider.recipeResponse.recipe.idMainIngredient
            if(idmainIngredient.equals(ingredient.idIngredient)){
                mainIngredient = ingredient.ingredientname
            }
            adapterRVIngredientsFinded.setSpinnerMainIngredient()
        }
        binding.spMainIngredient.setSelection(adapterRVIngredientsFinded.mainIngerdientsList.indexOf(mainIngredient))
        for(position in 0..RecipeProvider.recipeResponse.stepList.count()-1){
            val step = RecipeProvider.recipeResponse.stepList[position]
            adaprteSteps.addStep(step.instruction)
        }

    }

    private fun validateImage(): Boolean {
        if(binding.imgRecipe.drawable != null)
            return true
        return false
    }

    private  fun setListenerBtnSave() {
        binding.btnSaveRecipe.setOnClickListener{
            var ingredientSelectedList= adapterRVIngredientsFinded.getIngredientSelectedList()
            var x =0
            if(!binding.recipeName.text.isNullOrBlank()){
                if(validateImage()){
                    if(ingredientSelectedList.getContextIngredientSelectedAdapter().ingredientSelected.size != 0){
                        if(validateMeasureSelected())
                            if(validateSteps())
                                if(validatePortions())
                                    saveRecipe()
                                else{
                                    Toast.makeText(this,"Se necesita la cantidad de porciones",
                                        Toast.LENGTH_SHORT).show()
                                    binding.etPortions.text.clear()
                                }
                            else
                                Toast.makeText(this,"ingresa pasos", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(this,"ingresa las unidades", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(this,"Selecciona almenos un ingrediente", Toast.LENGTH_SHORT).show()
                }else
                    Toast.makeText(this,"Imagen obligatoria", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(this,"Nombre obligatorio", Toast.LENGTH_SHORT).show()

        }
    }

    //crea el objeto que será enviado a la api
    private  fun saveRecipe() {
        var idMainIngredient : String = ""
        for (item in IngredientProvider.ingredients){
            if(item.ingredientname.equals(binding.spMainIngredient.selectedItem.toString()))
                idMainIngredient = item.idIngredient
        }
        //objeto de receta
        var newRecipe : RecipeDomain = RecipeDomain(
            RecipeProvider.recipeResponse.recipe.idRecipe,
            "123",
            binding.recipeName.text.toString(),
            "",
            "00:00:00",
            idMainIngredient,
            binding.etPortions.text.toString().toInt(),
            imageViewToByteArray()
        )
        var instructions : MutableList<CookinginstructionDomain> = mutableListOf()
        //lista de pasos en la receta
        val adapterSteps = binding.rvCookingSteps.adapter as CookingStepAdapter

        for (position in 0 until adapterSteps.itemCount) {
            val viewHolder = binding.rvCookingSteps.findViewHolderForAdapterPosition(position) as? CookingStepAdapter.StepViewHolder
            // Verifica si el ViewHolder es nulo
            if (viewHolder != null) {
                var step : CookinginstructionDomain = CookinginstructionDomain(
                    "",
                    viewHolder.stepTextView.text.toString(),
                    position,
                    ""
                )
                instructions.add(step)
            }
        }
        //OBJETO DE CATEGORIA
        // var idCategorySelected : String = ""
        var categorySelected : Category = Category("","")
        for (item in CategoryProvider.categories){
            if(item.categoryName.equals(binding.cbCategories.selectedItem.toString())){
                categorySelected.idCategory = item.idCategory
                categorySelected.categoryName = item.categoryName
            }
        }
        //lista de ingredientes
        var recipeHasIngredientList : MutableList<RecipeHasIngredient> = mutableListOf()
        val ingredientsSelectedAdapter = binding.rvIngredientSelected.adapter as EditIngredientSelectedAdapter
        for (position in 0 until ingredientsSelectedAdapter.itemCount) {
            val viewHolder = binding.rvIngredientSelected.findViewHolderForAdapterPosition(position) as?  EditIngredientSelectedAdapter.EditIngredientSelectedViewHolder
            // Verifica si el ViewHolder es nulo
            if (viewHolder != null) {
                for(item in IngredientProvider.ingredients){
                    if(item.ingredientname.equals(viewHolder.ingredientTextView.text)){
                        var recipeHasIngerdient : RecipeHasIngredient = RecipeHasIngredient(
                            item.idIngredient,
                            "",
                            viewHolder.etMeasure.text.toString().toInt()
                        )
                        recipeHasIngredientList.add(recipeHasIngerdient)
                    }
                }

            }
        }

        //crear objeto a mandar
        var newRecipePost : NewRecipePost = NewRecipePost(
            newRecipe,
            instructions,
            categorySelected,
            recipeHasIngredientList
        )
        recipeViewModel.newRecipe = newRecipePost
        recipeViewModel.editRecipe()
        recipeViewModel.recipeViewModel.observe(this){
            if (recipeViewModel.httpCodeCreateRecipe == 200)
                Toast.makeText(this,"TODO BIEN", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"TODO MAL: {${recipeViewModel.httpCodeCreateRecipe}}" , Toast.LENGTH_SHORT).show()

        }
    }

    private fun imageViewToByteArray(): ByteArray {
        val imageView = binding.imgRecipe // Asegúrate de tener una referencia a tu ImageView

        // Extraer el drawable de la imagen desde el ImageView
        val drawable = imageView.drawable

        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap

            // Convertir el Bitmap en un arreglo de bytes
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        }
        val byteArrayOutputStreamEmpty = ByteArrayOutputStream()
        return byteArrayOutputStreamEmpty.toByteArray() // Devuelve null si no se pudo obtener un arreglo de bytes
    }




    /*
    private fun getImage(): String {
 /*
        val drawable = binding.imgRecipe.drawable

        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap

            // Convertir el Bitmap en una cadena Base64
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val imageBase64 = Base64.getEncoder().encodeToString(byteArray)
            println(imageBase64)
            return imageBase64
        } else {
            // Manejar el caso en el que no hay una imagen en el ImageView
            // Puedes retornar un valor por defecto o manejar la situación según tu lógica.
            return ""
        }*/
        val imageView = binding.imgRecipe // Asegúrate de tener una referencia a tu ImageView

        // Extraer el drawable de la imagen desde el ImageView
        val drawable = imageView.drawable

        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap

            // Convertir el Bitmap en una cadena Base64
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            println(Base64.getEncoder().encodeToString(byteArray))
            return Base64.getEncoder().encodeToString(byteArray)
        } else {
            // Manejar el caso en el que no hay una imagen en el ImageView
            // Puedes retornar un valor por defecto o manejar la situación según tu lógica.
            return ""
        }
    }


 */
    //valida que el string sea numerico
    private fun validatePortions(): Boolean {
        val regex = """^-?[1-9]\d*$""".toRegex()
        return regex.matches(binding.etPortions.text)
    }

    fun validateMeasureSelected (): Boolean{
        var band :Boolean = true
        val adapter = binding.rvIngredientSelected.adapter as EditIngredientSelectedAdapter

        val regex = """^-?[1-9]\d*$""".toRegex()
        for (position in 0 until adapter.itemCount) {
            val viewHolder = binding.rvIngredientSelected.findViewHolderForAdapterPosition(position) as? EditIngredientSelectedAdapter.EditIngredientSelectedViewHolder

            // Verifica si el ViewHolder es nulo
            if (viewHolder != null) {
                val editText = viewHolder.etMeasure
                val text = editText.text.toString()
                if (text.isNullOrBlank() || !regex.matches(text)) {//por si ingresa el valor 0
                    // Si algún EditText está vacío, retorna false
                    band = false
                }
            }
        }
        return band
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
        adapterRVIngredientsFinded = EditIngredientAdapter()
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
    private lateinit var adapterRVIngredientsFinded: EditIngredientAdapter
    private fun setMatchIngredients() {
        adapterRVIngredientsFinded.searchIngredientMatch(binding.SearchBar.text.toString(), binding, this)
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