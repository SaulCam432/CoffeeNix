package com.example.coffeenix.activities.Cliente.address.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.address.create.ClientAddressCreateActivity
import com.example.coffeenix.adapters.address.AddressAdapter
import com.example.coffeenix.adapters.categories.CategoriesAdapter
import com.example.coffeenix.databinding.ActivityClientAddressListBinding
import com.example.coffeenix.models.Address
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.AddressProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientAddressListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientAddressListBinding
    var addressProvider: AddressProvider? = null
    var sharedPref: SharedPref? = null
    var user: User? = null
    var address = ArrayList<Address>()
    var adapter: AddressAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getUserFromSession()

        addressProvider = AddressProvider(user?.sessionToken!!)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.clientAddressListToolbar)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        getAddress()

        binding.fabAddressCreate.setOnClickListener {
            val i = Intent(this, ClientAddressCreateActivity::class.java)
            startActivity(i)
        }
    }

    private fun getAddress(){
        addressProvider?.getAddressByUser(user?.id!!)?.enqueue(object : Callback<ArrayList<Address>> {
            override fun onResponse(call: Call<ArrayList<Address>>, response: Response<ArrayList<Address>>) {
                if (response.body() != null) {

                    address = response.body()!!
                    binding.recyclerViewAddressList.layoutManager = LinearLayoutManager(this@ClientAddressListActivity)
                    binding.recyclerViewAddressList.setHasFixedSize(true)
                    adapter = AddressAdapter(this@ClientAddressListActivity, address)
                    binding.recyclerViewAddressList.adapter = adapter

                    messageSuccess(user?.id.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<Address>>, t: Throwable) {
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