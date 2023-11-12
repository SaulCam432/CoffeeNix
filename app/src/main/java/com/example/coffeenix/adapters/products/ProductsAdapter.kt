package com.example.coffeenix.adapters.products

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.models.Product

class ProductsAdapter (val context: Activity, val product: ArrayList<Product>):
    RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(layoutInflater.inflate(R.layout.cardview_product, parent, false))
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val productsItems = product[position]
        holder.bind(context, productsItems)
    }

}