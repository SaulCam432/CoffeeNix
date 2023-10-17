package com.example.coffeenix

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.coffeenix.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.RegisterTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        /*
        * Boton de registro
        */
        binding.btnRegister.setOnClickListener {
            register()
        }

    }

    private fun register() {
        val name = binding.registerEditTextName.text.toString()
        val phone = binding.registerEditTextPhone.text.toString()
        val email = binding.registerEditTextEmail.text.toString()
        val password = binding.registerEditTextPsw.text.toString()
        val confirmPassword = binding.registerEditTextConfirmPsw.text.toString()

        if (isValidForm(name = name, phone = phone, email = email, password = password, confirmPassword = confirmPassword)) {
            Toast.makeText(this, "El formulario es valido", Toast.LENGTH_SHORT).show()
        }

    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isValidForm(
        name: String,
        phone: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (name.isBlank()) {
            Toast.makeText(this, "Debes ingresar el nombre", Toast.LENGTH_SHORT).show()
            return false
        }

        if (phone.isBlank()) {
            Toast.makeText(this, "Debes ingresar el telefono", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isBlank()) {
            Toast.makeText(this, "Debes ingresar el email", Toast.LENGTH_SHORT).show()
            return false
        }

        if(password.length > 8){
            Toast.makeText(this, "La contraseña es menor a 8 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isBlank()) {
            Toast.makeText(this, "Debes ingresar el contraseña", Toast.LENGTH_SHORT).show()
            return false
        }

        if (confirmPassword.isBlank()) {
            Toast.makeText(this, "Debes ingresar el la confirmacion de contraseña", Toast.LENGTH_SHORT).show()
            return false
        }


        if (!email.isEmailValid()) {
            Toast.makeText(this, "El email no es valido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun goToLogin(){
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }


}