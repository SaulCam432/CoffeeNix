package com.example.coffeenix.delivery.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityDeliveryHomeBinding
import com.example.coffeenix.databinding.ActivitySelectRolesBinding

class DeliveryHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}