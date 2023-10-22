package com.example.coffeenix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeenix.adapters.roles.RolesAdapter
import com.example.coffeenix.databinding.ActivitySelectRolesBinding
import com.example.coffeenix.models.Rol
import com.example.coffeenix.models.User
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectRolesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectRolesBinding
    var user: User? = null
    var adapter: RolesAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectRolesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.SelectRolesTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo


        getUserFromSession()

        recycleViewRoles()
    }

    private fun getUserFromSession(){
        val sharedPref = SharedPref(this)
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARiO EXISTE EN SESSION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)

        }
    }

    private fun recycleViewRoles(){
        binding.recyclerViewSelecRoles.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSelecRoles.setHasFixedSize(true)
        adapter = RolesAdapter(this, user?.roles!!)
        binding.recyclerViewSelecRoles.adapter = adapter
    }
}