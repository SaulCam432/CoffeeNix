package com.example.coffeenix.delivery.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityDeliveryHomeBinding
import com.example.coffeenix.fragments.client.ClientProfileFragment
import com.example.coffeenix.fragments.delivery.DeliveryOrdersFragment
import com.example.coffeenix.models.User
import com.example.coffeenix.utils.showMessage
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson

class DeliveryHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryHomeBinding
    var sharedPref: SharedPref? = null
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openFragment(DeliveryOrdersFragment())

        binding.bottomNavigationDelivery.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemOrderDelivery -> {
                    openFragment(DeliveryOrdersFragment())
                    true
                }

                R.id.itemProfileDelivery -> {
                    openFragment(ClientProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARiO EXISTE EN SESSION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d("TAG", "Usuario: ${user}")
        }
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}