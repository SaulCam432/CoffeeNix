package com.example.coffeenix.providers

import com.example.coffeenix.api.ApiRoutes
import com.example.coffeenix.models.Address
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.routes.AddressRoutes
import com.example.coffeenix.routes.CategoriesRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import java.io.File

class AddressProvider(val token: String) {

    private var addressRoutes: AddressRoutes? = null

    init {
        val api = ApiRoutes()
        addressRoutes = api.getAddressRoutes(token)
    }

    fun create(address: Address): Call<ResponseHttp>? {
        return addressRoutes?.create(address, token)
    }

    fun getAddressByUser(idUser: String): Call<ArrayList<Address>>? {
        return addressRoutes?.getByUser(idUser, token)
    }
}