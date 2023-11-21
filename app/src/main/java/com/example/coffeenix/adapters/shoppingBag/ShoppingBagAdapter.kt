package com.example.coffeenix.adapters.shoppingBag

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.R
import com.example.coffeenix.activities.Cliente.products.details.ClientProductDetailActivity
import com.example.coffeenix.activities.Cliente.shoppingBag.ClientShoppingBagActivity
import com.example.coffeenix.adapters.products.ProductsViewHolder
import com.example.coffeenix.databinding.CardviewProductBinding
import com.example.coffeenix.databinding.CardviewShoppingBagBinding
import com.example.coffeenix.models.Product
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class ShoppingBagAdapter (val context: Activity, val products: ArrayList<Product>): RecyclerView.Adapter<ShoppingBagAdapter.ShoppingBagViewHolder>() {

    val sharedPref = SharedPref(context)


    init {
        (context as ClientShoppingBagActivity).setTotal(getTotal())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_shopping_bag, parent, false)
        return ShoppingBagViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ShoppingBagViewHolder, position: Int) {

        val product = products[position] // CADA UNA DE LAS CATEGORIAS

        holder.textViewName.text = product.name
        holder.textViewCounter.text = "${product.quantity}"

        if (product.quantity != null) {
            holder.textViewPrice.text = "$${product.price * product.quantity!!}"
        }
        Picasso.get().load(product.image1).into(holder.imageViewProduct)

        holder.imageViewAdd.setOnClickListener { addItem(product, holder) }
        holder.imageViewRemove.setOnClickListener { removeItem(product, holder) }
        holder.imageViewDelete.setOnClickListener { deleteItem(position) }
//        holder.itemView.setOnClickListener { goToDetail(product) }
    }

    private fun getTotal(): Double {
        var total = 0.0
        for (p in products) {
            if (p.quantity != null) {
                total = total + (p.quantity!! * p.price)
            }
        }
        return total
    }

    private fun getIndexOf(idProduct: String): Int {
        var pos = 0

        for (p in products) {
            if (p.id == idProduct) {
                return pos
            }
            pos++
        }

        return -1
    }

    private fun deleteItem(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeRemoved(position, products.size)
        sharedPref.save("order", products)
        (context as ClientShoppingBagActivity).setTotal(getTotal())
    }

    private fun addItem(product: Product, holder: ShoppingBagViewHolder) {

        val index = getIndexOf(product.id!!)
        product.quantity = product.quantity!! + 1
        products[index].quantity = product.quantity

        holder.textViewCounter.text = "${product.quantity}"
        holder.textViewPrice.text = "$${product.quantity!! * product.price}"

        sharedPref.save("order", products)
        (context as ClientShoppingBagActivity).setTotal(getTotal())
    }

    private fun removeItem(product: Product, holder: ShoppingBagViewHolder) {

        if (product.quantity!! > 1) {

            val index = getIndexOf(product.id!!)
            product.quantity = product.quantity!! - 1
            products[index].quantity = product.quantity

            holder.textViewCounter.text = "${product.quantity}"
            holder.textViewPrice.text = "$${product.quantity!! * product.price}"

            sharedPref.save("order", products)
            (context as ClientShoppingBagActivity).setTotal(getTotal())

        }

    }

    class ShoppingBagViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textViewName: TextView
        val textViewPrice: TextView
        val textViewCounter: TextView
        val imageViewProduct: ImageView
        val imageViewAdd: ImageView
        val imageViewRemove: ImageView
        val imageViewDelete: ImageView

        init {
            textViewName = view.findViewById(R.id.textShoppingBagName)
            textViewPrice = view.findViewById(R.id.textViewShoppingBagPrice)
            textViewCounter = view.findViewById(R.id.textViewShoppingBagCounter)
            imageViewProduct = view.findViewById(R.id.imageViewShoppingBagImg)
            imageViewAdd = view.findViewById(R.id.imageViewShoppingBagAdd)
            imageViewRemove = view.findViewById(R.id.imageViewShoppingBagRemove)
            imageViewDelete = view.findViewById(R.id.imageViewShoppingBagDelete)
        }

    }

}