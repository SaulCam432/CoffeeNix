package com.example.coffeenix.activities.Cliente.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityClientAddressListBinding

class ClientAddressListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientAddressListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}