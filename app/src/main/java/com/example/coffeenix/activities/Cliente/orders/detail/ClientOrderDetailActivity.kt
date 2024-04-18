package com.example.coffeenix.activities.Cliente.orders.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityClientOrderDetailBinding
import com.example.coffeenix.models.Order
import com.google.gson.Gson

class ClientOrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientOrderDetailBinding

    var order: Order? = null
    var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        order = gson.fromJson(intent.getStringExtra("order"), Order::class.java)

        binding.clientOrderDetailUser.text = order?.client?.name
        binding.clientOrderDetailAddress.text = order?.address?.address
        binding.clientOrderDetailTime.text = order?.timestamp
        binding.clientOrderDetailStatus.text = order?.status

        getTotal()

    }

    private fun getTotal(){
        var total = 0.0
        for (products in order?.products!!){
            total = total + (products.quantity!! * products.price)
        }

        binding.clientOrderDetailMount.text = "$${total}"
    }
}