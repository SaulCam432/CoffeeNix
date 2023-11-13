package com.example.coffeenix.adapters.shoppingBag

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.databinding.CardviewShoppingBagBinding
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class ShoppingBagViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewShoppingBagBinding.bind(view)

    fun bind(context: Activity, product: Product){
        val sharedPref = SharedPref(context)

        binding.textShoppingBagName.text = product.name
        binding.textViewShoppingBagCounter.text = "${product.quantity!!}"
        binding.textViewShoppingBagPrice.text = "$${product.price * product.quantity!!}"
        Picasso.get().load(product.image1).into(binding.imageViewShoppingBagImg)


    }

}