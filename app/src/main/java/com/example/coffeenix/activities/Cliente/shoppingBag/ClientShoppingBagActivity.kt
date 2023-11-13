package com.example.coffeenix.activities.Cliente.shoppingBag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.address.ClientAddressListActivity
import com.example.coffeenix.adapters.categories.CategoriesAdapter
import com.example.coffeenix.adapters.shoppingBag.ShoppingBagAdapter
import com.example.coffeenix.databinding.ActivityClientShoppingBagBinding
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ClientShoppingBagActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientShoppingBagBinding
    var recyclerViewShoppingBag: RecyclerView? = null
    var adapter: ShoppingBagAdapter? = null
    val gson = Gson()
    var sharedPref: SharedPref? = null
    var selectedProduct = ArrayList<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientShoppingBagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.clientShoppingBagTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        getProductFromSharedPref()

        binding.btnBagToAddress.setOnClickListener { goToAdressList() }
    }

    private fun goToAdressList(){
        val i = Intent(this, ClientAddressListActivity::class.java)
        startActivity(i)
    }

    private fun getProductFromSharedPref() {

        if (!sharedPref?.getData("order").isNullOrBlank()) {
            val type = object : TypeToken<ArrayList<Product>>() {}.type
            selectedProduct = gson.fromJson(sharedPref?.getData("order"), type)

            binding.recyclerViewShoppingBag.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewShoppingBag.setHasFixedSize(true)
            adapter = ShoppingBagAdapter(this, selectedProduct)
            binding.recyclerViewShoppingBag.adapter = adapter

        }
    }

    fun setTotal(total: Double) {
        binding.textviewTotal.text = "${total}$"
    }


}