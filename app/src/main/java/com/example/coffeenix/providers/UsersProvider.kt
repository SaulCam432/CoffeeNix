package com.example.coffeenix.providers

import com.example.coffeenix.api.ApiRoutes
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.routes.UsersRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class UsersProvider {

    private var userRoutes: UsersRoutes?= null

    //CONSTRUCTOR
    init {
        val api = ApiRoutes()
        userRoutes = api. getUsersRoutes()
    }

    fun register(user: User): Call<ResponseHttp>?{
        return userRoutes?.register(user)
    }

    fun login(email: String, password: String): Call<ResponseHttp>?{
        return userRoutes?.login(email,password)
    }

    fun update(file: File, user: User): Call<ResponseHttp>? {

    //val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    //val image = MultipartBody.Part.createFormData("image", file.name, reqFile)
    //val requestBody = user.toJson().toRequestBody("text/plain".toMediaTypeOrNull())

    val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
    val image = MultipartBody.Part.createFormData("image", file.name, reqFile)
    val requestBody = RequestBody.create(MediaType.parse("text/plain"), user.toJson())

    return userRoutes?.update(image, requestBody)
    }
}