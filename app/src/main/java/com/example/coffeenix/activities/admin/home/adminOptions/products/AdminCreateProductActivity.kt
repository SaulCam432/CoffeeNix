package com.example.coffeenix.activities.admin.home.adminOptions.products

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.activities.admin.home.AdminHomeActivity
import com.example.coffeenix.databinding.ActivityAdminCreateCategoryBinding
import com.example.coffeenix.databinding.ActivityAdminCreateProductBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.Product
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.CategoriesProvider
import com.example.coffeenix.providers.ProductsProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AdminCreateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminCreateProductBinding
    val TAG = "CreateProduct"
    var sharedPref: SharedPref? = null
    var user: User? = null
    var categoriesProvider: CategoriesProvider ?= null
    var categories = ArrayList<Category>()
    var idCategory = ""

    var imageFile1: File? = null
    var imageFile2: File? = null
    var imageFile3: File? = null

    var productsProvider: ProductsProvider? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getUserFromSession()

        categoriesProvider = CategoriesProvider(user?.sessionToken!!)
        productsProvider = ProductsProvider(user?.sessionToken!!)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.adminProductToolbarTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        getCategories()

        binding.adminImageViewImage1.setOnClickListener { selectImage(101) }
        binding.adminImageViewImage2.setOnClickListener { selectImage(102) }
        binding.adminImageViewImage3.setOnClickListener { selectImage(103) }

        binding.adminProductAddBtn.setOnClickListener { createProduct()}
    }

    private fun createProduct(){
        val name = binding.adminProductEditTextName.text.toString()
        val price = binding.adminProductEditTextPrice.text.toString()
        val description = binding.adminProductsEditTextDes.text.toString()

        val files = ArrayList<File>()

        if (isValidForm(name, price, description)){
            val product = Product(
                name = name,
                description = description,
                price = price.toDouble(),
                idCategory = idCategory
            )

            files.add(imageFile1!!)
            files.add(imageFile2!!)
            files.add(imageFile3!!)


            productsProvider?.create(files, product)?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {

                    Log.d(TAG, "Response: $response")
                    Log.d(TAG, "Body: ${response.body()}")

                    messageSuccess(response.body()?.message.toString())


                    if (response.body()?.isSuccess == true) {
                        goToAdminHome()
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Error: ${t.message}")
                    messageError("Error: ${t.message}")
                }

            })
        }
    }

    private fun goToAdminHome(){
        val i = Intent(this, AdminHomeActivity::class.java)
        startActivity(i)
    }

    private fun getCategories(){
        categoriesProvider?.getAll()?.enqueue(object: Callback<ArrayList<Category>> {
            override fun onResponse(call: Call<ArrayList<Category>>, response: Response<ArrayList<Category>>) {
                if (response.body() != null){
                    categories = response.body()!!

                    val arrayAdapter = ArrayAdapter<Category>(this@AdminCreateProductActivity, android.R.layout.simple_dropdown_item_1line, categories)
                    binding.adminProductSpinnerCategory.adapter = arrayAdapter
                    binding.adminProductSpinnerCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(adapterview: AdapterView<*>?, view: View?, position: Int, l: Long) {
                            idCategory = categories[position].id!!
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Category>>, t: Throwable) {
                messageError("Error: ${t.message}")
            }

        })
    }

    private fun isValidForm(name: String, price: String, description: String): Boolean{

        if (name.isBlank()) {
            messageError("Debes ingresar el nombre")
            return false
        }

        if (price.isBlank()) {
            messageError("Debes ingresar el precio")
            return false
        }

        if (description.isBlank()) {
            messageError("Debes ingresar la descripción")
            return false
        }

        return true
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val fileUri = data?.data

            if (requestCode == 101) {
                imageFile1 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                binding.adminImageViewImage1.setImageURI(fileUri)
            }
            else if (requestCode == 102) {
                imageFile2 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                binding.adminImageViewImage2.setImageURI(fileUri)
            }

            else if (requestCode == 103) {
                imageFile3 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                binding.adminImageViewImage3.setImageURI(fileUri)
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



    private fun selectImage(requestCode: Int) {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(requestCode)
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}