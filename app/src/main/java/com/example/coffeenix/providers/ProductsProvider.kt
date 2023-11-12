package com.example.coffeenix.providers

import com.example.coffeenix.api.ApiRoutes
import com.example.coffeenix.models.Category
import com.example.coffeenix.models.Product
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.routes.CategoriesRoutes
import com.example.coffeenix.routes.ProductsRoutes
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import java.io.File

class ProductsProvider(val token: String) {

    private var productsRoutes: ProductsRoutes? = null

    init {
        val api = ApiRoutes()
        productsRoutes = api.getProductsRoutes(token)
    }

    fun create(files: List<File>, product: Product): Call<ResponseHttp>? {
        val images = arrayOfNulls<MultipartBody.Part>(files.size)
        for(i in 0 until files.size){
            val reqFile = RequestBody.create(MediaType.parse("image/*"), files[i])
            images[i] = MultipartBody.Part.createFormData("image", files[i].name, reqFile)
        }

        val requestBody = RequestBody.create(MediaType.parse("text/plain"), product.toJson())

        return productsRoutes?.create(images, requestBody, token)
    }

    fun getByCategory(idCategory: String): Call<ArrayList<Product>>? {
        return productsRoutes?.getByCategory(idCategory, token)
    }
}