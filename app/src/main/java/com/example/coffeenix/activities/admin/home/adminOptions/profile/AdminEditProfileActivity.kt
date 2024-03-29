package com.example.coffeenix.activities.admin.home.adminOptions.profile

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.coffeenix.R
import com.example.coffeenix.databinding.ActivityAdminEditProfileBinding
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.UsersProvider
import com.example.coffeenix.utils.SharedPref
import com.example.coffeenix.utils.showMessage
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AdminEditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEditProfileBinding
    val TAG = "AdminProfile"
    private var imageFile: File? = null
    var usersProvider: UsersProvider?= null
    var user: User? = null
    var sharedPref: SharedPref? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)
        getUserFromSession()

        usersProvider = UsersProvider(user?.sessionToken)

        /*
        * Implementacion de barra de herramientas
        */
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.textColor))
        binding.toolbar.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.toolbar.setTitle(R.string.adminEditProfileToolbarTitle)
        binding.toolbar.toolbar.setTitleTextAppearance(this, R.style.ActionBarTitle) //Cambiar tipo de letra y tamaño del titulo
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Boton pantalla anterior

        binding.adminUpdateEditTextName.setText(user?.name)
        binding.adminUpdateEditTextPhone.setText(user?.phone)

        if (!user?.image.isNullOrBlank()) {
            Picasso.get().load(user?.image).into(binding.adminUpdateImageView);
        }

        binding.adminUpdateImageView.setOnClickListener { selectImage() }

        binding.adminUpdateBtn.setOnClickListener {
            updateAdminUser()
        }

    }

    private fun isValidForm(
        name: String,
        phone: String
    ): Boolean {

        if (name.isBlank()) {
            messageError("Debes ingresar el nombre")
            return false
        }

        if (phone.isBlank()) {
            messageError("Debes ingresar el telefono")
            return false
        }

        return true
    }

    private fun updateAdminUser() {
        val name = binding.adminUpdateEditTextName.text.toString()
        val phone = binding.adminUpdateEditTextPhone.text.toString()

        if (isValidForm(name, phone)){
            user?.name = name
            user?.phone = phone

            if (imageFile != null){
                usersProvider?.update(imageFile!!, user!!)?.enqueue(object: Callback<ResponseHttp> {
                    override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                        Log.d(TAG, "RESPONSE: $response")
                        Log.d(TAG, "BODY: ${response.body()}")

                        if (response.body()?.isSuccess == true){
                            messageSuccess(response.body()?.message.toString())
                            saveUserInSession(response.body()?.data.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                        Log.d(TAG, "Se produjo un error ${t.message}")
                        messageError("Se produjo un error ${t.message}")
                    }

                })
            }else{
                usersProvider?.updateWithoutImage(user!!)?.enqueue(object: Callback<ResponseHttp> {
                    override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                        Log.d(TAG, "RESPONSE: $response")
                        Log.d(TAG, "BODY: ${response.body()}")

                        if (response.body()?.isSuccess == true){
                            messageSuccess(response.body()?.message.toString())
                            saveUserInSession(response.body()?.data.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                        Log.d(TAG, "Se produjo un error ${t.message}")
                        messageError("Se produjo un error ${t.message}")
                    }

                })
            }
        }

    }

    private val startImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data
                imageFile = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                binding.adminUpdateImageView.setImageURI(fileUri)
            }
            else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Tarea se cancelo", Toast.LENGTH_LONG).show()
            }

        }


    private fun selectImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
    }

    private fun getUserFromSession(){
        val gson = Gson()
        if(!sharedPref?.getData("user").isNullOrBlank()){
            //SI EL USUARiO EXISTE EN SESSION
            user = gson.fromJson(sharedPref?.getData("user"), User::class.java)
            Log.d(TAG, "Usuario: ${user}")
        }
    }

    private fun messageSuccess(message: String){
        Toast(this).showMessage(message, this, "success")
    }

    private fun messageError(message: String){
        Toast(this).showMessage(message, this, "error")
    }

    private fun saveUserInSession(data: String) {
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref?.save("user", user)
    }
}