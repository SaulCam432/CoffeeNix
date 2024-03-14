package com.example.coffeenix.adapters.clientOrders

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.activities.Cliente.products.list.ClientProductListActivity
import com.example.coffeenix.databinding.CardviewCategoriesBinding
import com.example.coffeenix.databinding.CardviewOrdersBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.Order
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class OrdersViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewOrdersBinding.bind(view)

    fun bind(context: Activity, order: Order){


        binding.cardviewOrdersStatusDate.text = order.timestamp
        binding.cardviewOrdersStatusAddress.text = order.address?.address
        binding.cardViewOrder.setOnClickListener {

        }

        getTotal(order)

    }

    fun getTotal(order: Order){
        var total = 0.0
        for (p in order?.products!!){
            total = total + (p.price * p.quantity!!)
        }

        binding.cardviewOrdersStatusPay.text = "${total}"
    }

    private fun goToDetailOrder(context: Activity){

    }
}