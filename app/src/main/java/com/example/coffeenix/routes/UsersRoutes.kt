package com.example.coffeenix.routes

import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersRoutes {

    @POST("users/create")
    fun register(@Body user: User): Call<ResponseHttp>

}