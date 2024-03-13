package com.example.coffeenix.routes

import com.example.coffeenix.models.Order
import com.example.coffeenix.models.ResponseHttp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OrdersRoutes {
    @POST("orders/create")
    fun create(
        @Body order: Order,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

    @GET("orders/findByStatus/{status}")
    fun getOrdersByStatus(
        @Path("status") status: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Order>>
}