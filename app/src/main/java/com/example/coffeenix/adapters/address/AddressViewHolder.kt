package com.example.coffeenix.adapters.address

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.activities.Cliente.home.ClientHomeActivity
import com.example.coffeenix.activities.admin.home.AdminHomeActivity
import com.example.coffeenix.activities.delivery.home.DeliveryHomeActivity
import com.example.coffeenix.databinding.CardviewAddressBinding
import com.example.coffeenix.databinding.CardviewRolesBinding
import com.example.coffeenix.models.Rol
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class AddressViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewAddressBinding.bind(view)


    fun bind(context: Activity, rol: Rol){

    }

}