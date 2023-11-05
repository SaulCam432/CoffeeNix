package com.example.coffeenix.adapters.roles

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.activities.Cliente.home.ClientHomeActivity
import com.example.coffeenix.activities.admin.home.AdminHomeActivity
import com.example.coffeenix.activities.delivery.home.DeliveryHomeActivity
import com.example.coffeenix.databinding.CardviewRolesBinding
import com.example.coffeenix.models.Rol
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class RolesViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewRolesBinding.bind(view)


    fun bind(context: Activity, rol: Rol){

        binding.cardViewRolesText.text = rol.rol
        Picasso.get().load(rol.image).into(binding.cardViewRolesImage);


        binding.cardViewRolesBtn.setOnClickListener{
            goToRol(context, rol)
            //context = it.context
        }

    }

    private fun goToRol(context: Activity, rol: Rol){
        val sharedPref = SharedPref(context)
        if (rol.rol == "ADMIN"){
            sharedPref.save("rol", "ADMIN")
            val i = Intent(context, AdminHomeActivity::class.java)
            context.startActivity(i)
        }
        else if (rol.rol == "CLIENTE"){
            sharedPref.save("rol", "CLIENTE")
            val i = Intent(context, ClientHomeActivity::class.java)
            context.startActivity(i)
        }
        else if (rol.rol == "REPARTIDOR"){
            sharedPref.save("rol", "REPARTIDOR")
            val i = Intent(context, DeliveryHomeActivity::class.java)
            context.startActivity(i)
        }
    }


}
