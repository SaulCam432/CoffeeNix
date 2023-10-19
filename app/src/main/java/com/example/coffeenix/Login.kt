package com.example.coffeenix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.coffeenix.databinding.ActivityLoginBinding
import kotlin.math.log

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtnRegister.setOnClickListener{
            goToRegisterActivity()
        }

        binding.loginBtnLogin.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val email = binding.loginEditTextEmail.text.toString()
        val password = binding.loginEditTextPsw.text.toString()

        if(isValidForm(email = email, password = password)){
            messageSuccess("El formulario es valido")
        }
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isValidForm(
        email : String,
        password: String
    ): Boolean{
        if (email.isBlank()){
            messageError("Debes ingresar un correo")
            return false
        }

        if (password.length < 8){
            messageError("Contraseña menor a 8 caracteres")
            return false
        }

        if (password.isBlank()){
            messageError("Debes ingresar una contraseña")
            return false
        }

        if (!email.isEmailValid()){
            messageError("Debes ingresar un correo valido")
            return false
        }

        return true
    }

    private fun goToRegisterActivity() {
        val i = Intent(this, Register::class.java)
        startActivity(i)
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }
}