package com.example.coffeenix

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.coffeenix.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.RegisterTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle)
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun goToLogin(){
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }
}