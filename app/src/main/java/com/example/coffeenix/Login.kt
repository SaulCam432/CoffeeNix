package com.example.coffeenix

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.coffeenix.Cliente.home.ClientHomeActivity
import com.example.coffeenix.admin.home.AdminHomeActivity
import com.example.coffeenix.databinding.ActivityLoginBinding
import com.example.coffeenix.delivery.home.DeliveryHomeActivity
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.UsersProvider
import com.example.coffeenix.utils.SharedPref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var TAG = "ActivityLogin"
    var usersProvider = UsersProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserFromSession()

        binding.loginBtnRegister.setOnClickListener{
            goToRegisterActivity()
        }

        binding.loginBtnLogin.setOnClickListener {
            login()
        }
    }

    private fun goToClientHome(){
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK //eliminar istorial de pantallas
        startActivity(i)
    }

    private fun goToSelectRol(){
        val i = Intent(this, SelectRolesActivity::class.java)
        i.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK //eliminar istorial de pantallas
        startActivity(i)
    }

    private fun login(){
        val email = binding.loginEditTextEmail.text.toString()
        val password = binding.loginEditTextPsw.text.toString()

        if(isValidForm(email = email, password = password)){
            //messageSuccess("El formulario es valido")
            usersProvider.login(email, password)?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "response: ${response.body()?.message}")
                    if (response.body()?.isSuccess == true){
                        messageSuccess(response.body()?.message.toString())

                        saveUserInSession(response.body()?.data.toString())
                    }else{
                        messageError("Los datos no son correctos")
                    }
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Se produjo un error ${t.message}")
                    messageError("Se produjo un error ${t.message}")
                }
            })
        }
    }

    private fun saveUserInSession(data: String){
        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref.save("user", user)

        if (user.roles?.size!! > 1){ //EL USUARIO TIENE MAS ROLES
            goToSelectRol()
        }else{//EL USUARIO SOLO TIENE UN ROL (CLIENTE)
            goToClientHome()
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
        i.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK //eliminar istorial de pantallas
        startActivity(i)
    }

    private fun goToAdminHome() {
        val i = Intent(this, AdminHomeActivity::class.java)
        i.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK //eliminar istorial de pantallas
        startActivity(i)
    }

    private fun goToDeliveryHome() {
        val i = Intent(this, DeliveryHomeActivity::class.java)
        i.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK //eliminar istorial de pantallas
        startActivity(i)
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }


    private fun getUserFromSession(){
        val sharedPref = SharedPref(this)
        val gson = Gson()

        // SI EL USUARiO EXISTE EN SESSION
        if(!sharedPref.getData("user").isNullOrBlank()){
            val user = gson.fromJson(sharedPref.getData("user"), User::class.java)

            if(!sharedPref.getData("rol").isNullOrBlank()){
                val rol = sharedPref.getData("rol")?.replace("\"", "")

                if (rol == "ADMIN"){
                    goToAdminHome()
                }else if (rol == "CLIENTE"){
                    goToClientHome()
                } else if (rol == "REPARTIDOR"){
                    goToDeliveryHome()
                }
            }else {
                goToClientHome()
            }
        }
    }
}