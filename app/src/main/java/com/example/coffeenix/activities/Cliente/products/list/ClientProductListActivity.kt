package com.example.coffeenix.activities.Cliente.products.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeenix.R
import com.example.coffeenix.adapters.categories.CategoriesAdapter
import com.example.coffeenix.adapters.products.ProductsAdapter
import com.example.coffeenix.databinding.ActivityClientProductListBinding
import com.example.coffeenix.models.Product
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.ProductsProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientProductListBinding

    var sharedPref: SharedPref? = null
    var user: User? = null
    var adapter: ProductsAdapter? = null
    var productsProvider: ProductsProvider? = null
    var product: ArrayList<Product> = ArrayList()
    var idCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idCategory = intent.getStringExtra("idCategory")

        sharedPref = SharedPref(this)
        getUserFromSession()
        productsProvider = ProductsProvider(user?.sessionToken!!)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.clientProductToolbarTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        getProducts()
    }

    private fun getProducts(){
        productsProvider?.getByCategory(idCategory!!)?.enqueue(object : Callback<ArrayList<Product>> {
            override fun onResponse(call: Call<ArrayList<Product>>, response: Response<ArrayList<Product>>) {
                if (response.body() != null) {

                    product = response.body()!!
                    binding.recyclerViewProducts.layoutManager = GridLayoutManager(this@ClientProductListActivity, 2)
                    binding.recyclerViewProducts.setHasFixedSize(true)
                    adapter = ProductsAdapter(this@ClientProductListActivity, product)
                    binding.recyclerViewProducts.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                messageError(t.message.toString())
            }

        })
    }


    private fun getUserFromSession(){
        var gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
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