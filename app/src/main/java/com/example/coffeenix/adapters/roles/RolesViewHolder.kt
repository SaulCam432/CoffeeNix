package com.example.coffeenix.adapters.roles

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.databinding.CardviewRolesBinding
import com.example.coffeenix.models.Rol
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class RolesViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewRolesBinding.bind(view)

    fun bind(context: Activity, rol: Rol){
        val sharedPref = SharedPref(context)

        binding.cardViewRolesText.text = rol.rol
        Picasso.get().load(rol.image).into(binding.cardViewRolesImage);

    }
}
