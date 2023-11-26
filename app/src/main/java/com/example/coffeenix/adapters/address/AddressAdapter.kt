package com.example.coffeenix.adapters.address

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.shoppingBag.ClientShoppingBagActivity
import com.example.coffeenix.adapters.shoppingBag.ShoppingBagAdapter
import com.example.coffeenix.models.Address
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class AddressAdapter (val context: Activity, val address: ArrayList<Address>): RecyclerView.Adapter<AddressAdapter.AddressAdapterViewHolder>() {

    val sharedPref = SharedPref(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_address, parent, false)
        return AddressAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return address.size
    }

    override fun onBindViewHolder(holder: AddressAdapterViewHolder, position: Int) {

        val address = address[position] // CADA UNA DE LAS CATEGORIAS

        holder.textAddressName.text = address.address
        holder.textAddressRef.text = address.town

    }

    class AddressAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textAddressName: TextView
        val textAddressRef: TextView

        init {
            textAddressName = view.findViewById(R.id.textAddressName)
            textAddressRef = view.findViewById(R.id.textAddressRef)
        }

    }

}