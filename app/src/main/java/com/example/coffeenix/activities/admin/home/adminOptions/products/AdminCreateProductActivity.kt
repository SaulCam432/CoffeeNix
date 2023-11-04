package com.example.coffeenix.activities.admin.home.adminOptions.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityAdminCreateCategoryBinding
import com.example.coffeenix.databinding.ActivityAdminCreateProductBinding

class AdminCreateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminCreateProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.adminProductToolbarTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior
    }
}