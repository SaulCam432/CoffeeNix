package com.example.coffeenix.api

import com.example.coffeenix.routes.AddressRoutes
import com.example.coffeenix.routes.CategoriesRoutes
import com.example.coffeenix.routes.OrdersRoutes
import com.example.coffeenix.routes.ProductsRoutes
import com.example.coffeenix.routes.UsersRoutes
import retrofit2.create

class ApiRoutes {
    val API_URL = "http://192.168.1.183:3000/api/"
    val retrofit = RetrofitClient()
    fun getUsersRoutes(): UsersRoutes{
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

    fun getUsersRoutesWithToken(token: String): UsersRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(UsersRoutes::class.java)
    }

    fun getCategoriesRoutes(token: String): CategoriesRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(CategoriesRoutes::class.java)
    }

    fun getProductsRoutes(token: String): ProductsRoutes{
        return retrofit.getClientWithToken(API_URL, token).create(ProductsRoutes::class.java)
    }

    fun getAddressRoutes(token: String): AddressRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(AddressRoutes::class.java)
    }

    fun getOrdersRoutes(token: String): OrdersRoutes {
        return retrofit.getClientWithToken(API_URL, token).create(OrdersRoutes::class.java)
    }
}