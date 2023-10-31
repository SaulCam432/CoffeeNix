package com.example.coffeenix.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.coffeenix.Cliente.home.ClientHomeActivity
import com.example.coffeenix.databinding.ActivitySaveImageBinding
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.providers.UsersProvider
import com.example.coffeenix.utils.showMessage
import com.example.coffeenix.utils.SharedPref
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SaveImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveImageBinding
    val TAG = "SaveImageActivity"
    private var imageFile: File? = null
    var usersProvider = UsersProvider()
    var user: User? = null
    var sharedPref: SharedPref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref= SharedPref(this)
        getUserFromSession()

        binding.circleImageView.setOnClickListener { selectImage() }

        binding.saveImageBtnSave.setOnClickListener { saveImage() }
        binding.saveImageBtnClose.setOnClickListener { goToClientHome() }
    }

    private fun saveImage() {

        if (imageFile != null && user != null){
            usersProvider.update(imageFile!!, user!!)?.enqueue(object : Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                    Log.d(TAG, "RESPONSE: ${response.body()}")
                    Log.d(TAG, "BODY: ${response}")
                    saveUserInSession(response.body()?.data.toString())
                }

                override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Se produjo un error ${t.message}")
                    messageError("Se produjo un error ${t.message}")
                }

            })
        }else{
            messageError("La imagen y datos de sesion no pueder ser nulos")
        }

    }

    private val startImageForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data
                imageFile = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                binding.circleImageView.setImageURI(fileUri)
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
        goToClientHome()
    }

    private fun goToClientHome(){
        val i = Intent(this, ClientHomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //Eliminar historial de pantallas
        startActivity(i)
    }
}