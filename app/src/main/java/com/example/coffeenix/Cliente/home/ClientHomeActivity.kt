package com.example.coffeenix.Cliente.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffeenix.Login
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityClientHomeBinding
import com.example.coffeenix.models.User
import com.example.coffeenix.showMessage
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson

class ClientHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientHomeBinding
    var TAG = "ActivityClienteHome"
    var sharedPref: SharedPref? = null
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref= SharedPref(this)

        getUserFromSession()

        binding.clientHomeCloseSession.setOnClickListener {
            logOut()
        }
    }


    private fun goToLogin(){
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }

    private fun logOut(){
        sharedPref?.remove("user")
        goToLogin()
    }
    private fun getUserFromSession(){
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARiO EXISTE EN SESSION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "Usuario: ${user}")
        }
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}