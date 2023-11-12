package com.example.coffeenix.adapters.categories

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeenix.activities.Cliente.products.list.ClientProductListActivity
import com.example.coffeenix.databinding.CardviewCategoriesBinding
import com.example.coffeenix.models.Category
import com.example.coffeenix.utils.SharedPref
import com.squareup.picasso.Picasso

class CategoriesViewHolder (view: View): RecyclerView.ViewHolder(view) {
    private val binding = CardviewCategoriesBinding.bind(view)

    fun bind(context: Activity, category: Category){
        val sharedPref = SharedPref(context)

        binding.textViewCategories.text = category.name
        Picasso.get().load(category.image).into(binding.imageViewCategory);

        binding.cardviewCategoryItem.setOnClickListener { goToProducts(context, category) }

    }

    private fun goToProducts(context: Activity, category: Category) {
        val i = Intent(context, ClientProductListActivity::class.java)
        i.putExtra("idCategory", category.id)
        context.startActivity(i)
    }
}