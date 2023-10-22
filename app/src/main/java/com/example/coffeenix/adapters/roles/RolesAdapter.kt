package com.example.coffeenix.adapters.roles

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.models.Rol

class RolesAdapter(val context: Activity, val roles: ArrayList<Rol>) :
    RecyclerView.Adapter<RolesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RolesViewHolder(layoutInflater.inflate(R.layout.cardview_roles, parent, false))
    }

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {

        val rolesItem = roles[position]
        holder.bind(context, rolesItem)

    }


}