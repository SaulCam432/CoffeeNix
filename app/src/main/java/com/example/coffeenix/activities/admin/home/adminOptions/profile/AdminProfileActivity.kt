package com.example.coffeenix.activities.admin.home.adminOptions.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.activities.Login
import com.example.coffeenix.databinding.ActivityAdminProfileBinding
import com.example.coffeenix.models.User
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class AdminProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfileBinding
    var sharedPref: SharedPref? = null
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        getUserFromSession()

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.adminProfileToolbarTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        binding.adminProfileTextViewName.text = user?.name.toString()
        binding.adminProfileTextViewEmail.text = user?.email.toString()
        binding.adminProfileTextViewPhone.text = user?.phone.toString()

        if (!user?.image.isNullOrBlank()) {
            Picasso.get().load(user?.image).into(binding.adminProfileImageView);
        }

        binding.adminBtnEditProfile.setOnClickListener {
            var i = Intent(this, AdminEditProfileActivity::class.java)
            startActivity(i)
        }

        binding.adminBtnCloseSession.setOnClickListener {
            logout()
        }
    }

    private fun getUserFromSession(){
        var gson = Gson()
        if (!sharedPref?.getData("user").isNullOrBlank()){
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
        }
    }

    private fun logout() {
        sharedPref?.remove("user")
        sharedPref?.remove("rol")
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }
}