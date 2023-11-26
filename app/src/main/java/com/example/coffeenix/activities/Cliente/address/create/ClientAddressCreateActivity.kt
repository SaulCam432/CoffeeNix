package com.example.coffeenix.activities.Cliente.address.create

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.address.list.ClientAddressListActivity
import com.example.coffeenix.activities.Cliente.address.map.ClientAddressMapActivity
import com.example.coffeenix.databinding.ActivityClientAddressCreateBinding
import com.example.coffeenix.models.Address
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.AddressProvider
import com.example.coffeenix.routes.AddressRoutes
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientAddressCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientAddressCreateBinding

    val TAG = "CreateAddressActiv"

     var addressLat = 0.0
     var addressLng = 0.0

    var addresProvider: AddressProvider? = null
    var sharedPref: SharedPref? = null
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAddressCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getUserFromSession()

        addresProvider = AddressProvider(user?.sessionToken!!)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.clientAddressCreateToolbar)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        binding.clientAddressCreateRef.setOnClickListener { goToAddressMap() }

        binding.clientAddressCreateBtn.setOnClickListener { createAddress() }
    }

    private fun createAddress(){
        var addressName = binding.clientAddressCreateName.text.toString()
        var addressTown = binding.clientAddressCreateTown.text.toString()

        if (isValidForm(addressName, addressTown)){

            val addressModel = Address(
                address = addressName,
                town = addressTown,
                idUser = user?.id!!,
                lat = addressLat,
                lng = addressLng
            )

            addresProvider?.create(addressModel)?.enqueue(object : Callback<ResponseHttp>{
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    if (response.body() != null){
                        successMessage(response.body()?.message.toString())
                        goToAddressList()
                    }else{
                        errorMessage("Ocurrio un error en la peticion")
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    errorMessage("Error ${t.message}")
                }

            })
        }
    }

    private fun goToAddressList(){
        val i = Intent(this, ClientAddressListActivity::class.java)
        startActivity(i)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            val city = data?.getStringExtra("city")
            val address = data?.getStringExtra("address")
            val country = data?.getStringExtra("country")
            addressLat = data?.getDoubleExtra("lat", 0.0)!!
            addressLng = data?.getDoubleExtra("lng", 0.0)!!

            binding.clientAddressCreateRef.setText("$address $city")

            Log.d(TAG, "City $city")
            Log.d(TAG, "Address $address")
            Log.d(TAG, "Country $country")
            Log.d(TAG, "Lat $addressLat")
            Log.d(TAG, "Lng $addressLng")

        }
    }

    private fun goToAddressMap(){
        val i = Intent(this, ClientAddressMapActivity::class.java)
        resultLauncher.launch(i)
    }

    private fun isValidForm(address: String, town:String): Boolean {

        if (address.isNullOrBlank()){
            errorMessage("Debes ingresar el nombre de la dirección")
            return false
        }

        if (town.isNullOrBlank()){
            errorMessage("Debes ingresar un municipio")
            return false
        }

        if (addressLat == 0.0){
            errorMessage("Debes seleccionar un punto de referencia")
            return false
        }

        if(addressLng == 0.0){
            errorMessage("Debes selecionar un punto de referencia")
            return false
        }

        return true
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARiO EXISTE EN SESSION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)

        }
    }

    private fun successMessage(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun errorMessage(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}