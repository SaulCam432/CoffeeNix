package com.example.coffeenix.activities.Cliente.address.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.address.create.ClientAddressCreateActivity
import com.example.coffeenix.databinding.ActivityClientAddressListBinding

class ClientAddressListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientAddressListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.clientAddressListToolbar)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior



        binding.fabAddressCreate.setOnClickListener {
            val i = Intent(this, ClientAddressCreateActivity::class.java)
            startActivity(i)
        }
    }
}