package com.example.coffeenix.adapters.address

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.address.list.ClientAddressListActivity
import com.example.coffeenix.activities.Cliente.shoppingBag.ClientShoppingBagActivity
import com.example.coffeenix.adapters.shoppingBag.ShoppingBagAdapter
import com.example.coffeenix.models.Address
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class AddressAdapter (val context: Activity, val address: ArrayList<Address>): RecyclerView.Adapter<AddressAdapter.AddressAdapterViewHolder>() {

    val sharedPref = SharedPref(context)
    var gson = Gson()
    var prev = 0
    var positionAddressSession = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_address, parent, false)
        return AddressAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return address.size
    }

    override fun onBindViewHolder(holder: AddressAdapterViewHolder, position: Int) {

        val a = address[position] // CADA UNA DE LAS CATEGORIAS

        if (!sharedPref.getData("address").isNullOrBlank()){
            val adr = gson.fromJson(sharedPref.getData("address"), Address::class.java)

            if(adr.id == a.id){
                positionAddressSession = position
                holder.ImageAddressCheck.visibility = View.VISIBLE
            }
        }

        holder.textAddressName.text = a.address
        holder.textAddressRef.text = a.town

        holder.itemView.setOnClickListener {

            (context as ClientAddressListActivity).resetValue(prev)
            (context as ClientAddressListActivity).resetValue(positionAddressSession)
            prev = position // 1

            holder.ImageAddressCheck.visibility = View.VISIBLE
            saveAddress(a.toJson())
        }


    }

    class AddressAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textAddressName: TextView
        val textAddressRef: TextView
        val ImageAddressCheck: ImageView

        init {
            textAddressName = view.findViewById(R.id.textAddressName)
            textAddressRef = view.findViewById(R.id.textAddressRef)
            ImageAddressCheck = view.findViewById(R.id.imageViewAddresCheck)
        }

    }

    private fun saveAddress(data: String){
        val ad = gson.fromJson(data, Address::class.java)
        sharedPref.save("address", ad)
    }

}