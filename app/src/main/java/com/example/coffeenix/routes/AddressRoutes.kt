package com.example.coffeenix.routes

import com.example.coffeenix.models.Address
import com.example.coffeenix.models.Product
import com.example.coffeenix.models.ResponseHttp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AddressRoutes {

    @POST("address/create")
    fun create(
        @Body address: Address,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>

    @GET("address/getByUser/{id_user}")
    fun getByUser(
        @Path("id_user") idUser: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Address>>
}