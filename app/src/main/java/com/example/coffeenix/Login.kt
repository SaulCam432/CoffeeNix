package com.example.coffeenix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeenix.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtnRegister.setOnClickListener{
            goToRegisterActivity()
        }
    }

    private fun goToRegisterActivity() {
        val i = Intent(this, Register::class.java)
        startActivity(i)
    }
}