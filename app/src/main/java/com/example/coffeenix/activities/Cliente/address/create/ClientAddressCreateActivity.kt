package com.example.coffeenix.activities.Cliente.address.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.address.map.ClientAddressMapActivity
import com.example.coffeenix.databinding.ActivityClientAddressCreateBinding

class ClientAddressCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientAddressCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAddressCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.clientAddressCreateToolbar)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        binding.clientAddressCreateRef.setOnClickListener { goToAddressMap() }
    }

    private fun goToAddressMap(){
        val i = Intent(this, ClientAddressMapActivity::class.java)
        startActivity(i)
    }
}