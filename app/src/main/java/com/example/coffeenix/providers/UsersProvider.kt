package com.example.coffeenix.providers

import com.example.coffeenix.api.ApiRoutes
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.models.User
import com.example.coffeenix.routes.UsersRoutes
import retrofit2.Call

class UsersProvider {

    private var userRoutes: UsersRoutes?= null

    //CONSTRUCTOR
    init {
        val api = ApiRoutes()
        userRoutes = api.getUsersRoutes()
    }

    fun register(user: User): Call<ResponseHttp>?{
        return userRoutes?.register(user)
    }
}