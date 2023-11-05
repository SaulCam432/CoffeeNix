package com.example.coffeenix.activities.admin.home.adminOptions.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityAdminCreateCategoryBinding
import com.example.coffeenix.databinding.ActivityAdminCreateProductBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.CategoriesProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AdminCreateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminCreateProductBinding
    var sharedPref: SharedPref? = null
    var user: User? = null
    var categoriesProvider: CategoriesProvider ?= null
    var categories = ArrayList<Category>()
    var idCategory = ""

    var imageFile1: File? = null
    var imageFile2: File? = null
    var imageFile3: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getUserFromSession()

        categoriesProvider = CategoriesProvider(user?.sessionToken!!)

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
                            messageSuccess(idCategory)
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

    private fun getUserFromSession(){
        val gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
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