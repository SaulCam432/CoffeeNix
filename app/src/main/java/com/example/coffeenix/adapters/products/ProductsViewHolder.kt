package com.example.coffeenix.adapters.products

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.activities.Cliente.products.details.ClientProductDetailActivity
import com.example.coffeenix.databinding.CardviewProductBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class ProductsViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewProductBinding.bind(view)

    fun bind(context: Activity, product: Product){
        val sharedPref = SharedPref(context)

        binding.textViewProductName.text = product.name
        binding.textViewProductPrice.text = "$ ${product.price}"
        Picasso.get().load(product.image1).into(binding.imageViewProduct)

        binding.cardviewProductsItem.setOnClickListener { goToDetail(context, product) }

    }

    private fun goToDetail(context: Activity, product: Product) {
        val i = Intent(context, ClientProductDetailActivity::class.java)
        i.putExtra("product", product.toJson())
        context.startActivity(i)
    }
}
