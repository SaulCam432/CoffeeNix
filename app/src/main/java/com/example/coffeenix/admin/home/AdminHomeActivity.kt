package com.example.coffeenix.admin.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityAdminHomeBinding
import com.example.coffeenix.databinding.ActivityDeliveryHomeBinding

class AdminHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}