package com.example.coffeenix.api

import com.example.coffeenix.routes.UsersRoutes

class ApiRoutes {
    val API_URL = "http://192.168.1.183:3000/api/"
    val retrofit = RetrofitClient()
    fun getUsersRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

    fun getUsersRoutesWithToken(token: String): UsersRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(UsersRoutes::class.java)
    }
}