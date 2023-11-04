package com.example.coffeenix.activities.Cliente.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coffeenix.activities.Login
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityClientHomeBinding
import com.example.coffeenix.fragments.client.ClientCategoriesFragment
import com.example.coffeenix.fragments.client.ClientOrdersFragment
import com.example.coffeenix.fragments.client.ClientProfileFragment
import com.example.coffeenix.models.User
import com.example.coffeenix.utils.showMessage
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

        openFragment(ClientCategoriesFragment())

        binding.bottomNavigationClient.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.itemHome -> {
                    openFragment(ClientCategoriesFragment())
                    true
                }

                R.id.itemOrder -> {
                    openFragment(ClientOrdersFragment())
                    true
                }

                R.id.itemProfile -> {
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