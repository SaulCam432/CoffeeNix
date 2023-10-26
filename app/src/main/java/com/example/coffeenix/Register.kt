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
import com.example.coffeenix.Cliente.home.ClientHomeActivity
import com.example.coffeenix.databinding.ActivityRegisterBinding
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.UsersProvider
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    var usersProvider = UsersProvider()
    var TAG = "RegisterActivity"

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
            var user = User (
                name = name,
                email = email,
                phone = phone,
                password = password
            )
            
            usersProvider.register(user)?.enqueue(object : Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "Response ${response.body()?.message}")
                    Log.d(TAG, "Body ${response.body()}")

                    if(response.body()?.isSuccess == true){
                        messageSuccess(response.body()?.message.toString())
                        saveUserInSession(response.body()?.data.toString())
                        goToSaveImage()
                    }
                    else{
                        messageError("Error al crear usuario")
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Se produjo un error ${t.message}")
                    messageError("Se produjo un error ${t.message}")
                }
            })
        //messageSuccess("El formulario es valido")
        }

    }

    private fun goToClientHome(){
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas
        startActivity(i)
    }
    private fun goToSaveImage(){
        val i = Intent(this, SaveImageActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas
        startActivity(i)
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
            messageError("Debes ingresar el nombre")
            return false
        }

        if (phone.isBlank()) {
            messageError("Debes ingresar el telefono")
            return false
        }

        if (email.isBlank()) {
            messageError("Debes ingresar el email")
            return false
        }

        if(password.length < 8){
            messageError("La contraseña es menor a 8 caracteres")
            return false
        }

        if (password.isBlank()) {
            messageError("Debes ingresar el contraseña")
            return false
        }

        if (confirmPassword.isBlank()) {
            messageError("Debes ingresar el la confirmacion de contraseña")
            return false
        }


        if (!email.isEmailValid()) {
            messageError("El email no es valido")
            return false
        }

        if (password != confirmPassword) {
            messageError("Las contraseñas no coinciden")
            return false
        }

        return true
    }

    private fun saveUserInSession(data: String){
        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref.save("user", user)

    }

    private fun goToLogin(){
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }


}