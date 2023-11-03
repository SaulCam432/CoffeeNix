package com.example.coffeenix.admin.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeenix.activities.SelectRolesActivity
import com.example.coffeenix.admin.home.adminOptions.categories.AdminCreateCategoryActivity
import com.example.coffeenix.admin.home.adminOptions.products.AdminCreateProductActivity
import com.example.coffeenix.admin.home.adminOptions.orders.AdminOrdersActivity
import com.example.coffeenix.admin.home.adminOptions.profile.AdminProfileActivity
import com.example.coffeenix.databinding.ActivityAdminHomeBinding

class AdminHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AdminHomeReturn.setOnClickListener {
            goToSelectRoles()
        }

        binding.OptionsAdminCategory.setOnClickListener {
            goToCreateCategory()
        }

        binding.OptionsAdminOrders.setOnClickListener {
            goToOrders()
        }

        binding.OptionsAdminProducts.setOnClickListener {
            goToCreateProducts()
        }

        binding.OptionsAdminProfile.setOnClickListener {
            goToAdminProfile()
        }

    }

    private fun goToCreateCategory(){
        val i = Intent(this, AdminCreateCategoryActivity::class.java)
        startActivity(i)
    }

    private fun goToCreateProducts(){
        val i = Intent(this, AdminCreateProductActivity::class.java)
        startActivity(i)
    }

    private fun goToAdminProfile(){
        val i = Intent(this, AdminProfileActivity::class.java)
        startActivity(i)
    }

    private fun goToOrders(){
        val i = Intent(this, AdminOrdersActivity::class.java)
        startActivity(i)
    }

    private fun goToSelectRoles(){
        val i = Intent(this, SelectRolesActivity::class.java)
        startActivity(i)
    }
}