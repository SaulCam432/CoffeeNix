package com.example.coffeenix.activities.admin.home.adminOptions.categories

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.activities.admin.home.AdminHomeActivity
import com.example.coffeenix.databinding.ActivityAdminCreateCategoryBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.CategoriesProvider
import com.example.coffeenix.providers.UsersProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AdminCreateCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminCreateCategoryBinding
    val TAG = "CreateCategory"
    var categoriesProvider: CategoriesProvider? = null
    var sharedPref: SharedPref? = null
    var user: User? = null
    private var imageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getUserFromSession()

        categoriesProvider = CategoriesProvider(user?.sessionToken!!)


        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.adminCategoryToolbarTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        binding.adminImageViewCategory.setOnClickListener { selectImage() }

        binding.adminCategoryAddBtn.setOnClickListener { createCategory() }
    }

    private val startImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data
                imageFile = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                binding.adminImageViewCategory.setImageURI(fileUri)
            }
            else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Tarea se cancelo", Toast.LENGTH_LONG).show()
            }

        }


    private fun selectImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
    }

    private fun createCategory(){
        val name = binding.adminCategoryEditTextName.text.toString()
        if (isValidForm(name = name)){
            if (imageFile != null){
                val category = Category(name = name)

                categoriesProvider?.create(imageFile!!, category)?.enqueue(object: Callback<ResponseHttp> {
                    override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {

                        messageSuccess(response.body()?.message.toString())
                        if(response.body()?.isSuccess == true){
                            goToAdminHome()
                        }
                    }

                    override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                        messageError("Error ${t.message}")
                    }

                })
            }
            else{
                messageError("Debes seleccionar una imagen")
            }
        }
    }

    private fun isValidForm(name: String): Boolean{
        if (name.isBlank()) {
            messageError("Debes ingresar el nombre")
            return false
        }
        return true
    }

    private fun goToAdminHome(){
        val i = Intent(this, AdminHomeActivity::class.java)
        startActivity(i)
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARiO EXISTE EN SESSION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}