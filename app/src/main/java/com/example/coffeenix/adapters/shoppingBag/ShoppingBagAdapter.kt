package com.example.coffeenix.adapters.shoppingBag

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.products.details.ClientProductDetailActivity
import com.example.coffeenix.adapters.products.ProductsViewHolder
import com.example.coffeenix.databinding.CardviewProductBinding
import com.example.coffeenix.databinding.CardviewShoppingBagBinding
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class ShoppingBagAdapter (val context: Activity, val product: ArrayList<Product>):
    RecyclerView.Adapter<ShoppingBagViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBagViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShoppingBagViewHolder(layoutInflater.inflate(R.layout.cardview_shopping_bag, parent, false))
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: ShoppingBagViewHolder, position: Int) {
        val productsItems = product[position]
        holder.bind(context, productsItems)
    }

}