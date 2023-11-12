package com.example.coffeenix.routes

import com.example.coffeenix.models.Product
import com.example.coffeenix.models.ResponseHttp
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ProductsRoutes {

    @GET("products/findByCategory/{id_category}")
    fun getByCategory(
        @Path("id_category") idProduct: String,
        @Header("Authorization") token: String
    ): Call<ArrayList<Product>>

    @Multipart
    @POST("products/create")
    fun create(
        @Part image: Array<MultipartBody.Part?>,
        @Part("product") product: RequestBody,
        @Header("Authorization") token: String
    ): Call<ResponseHttp>
}