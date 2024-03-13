package com.example.coffeenix.providers

import com.example.coffeenix.api.ApiRoutes
import com.example.coffeenix.models.Order
import com.example.coffeenix.models.ResponseHttp
import com.example.coffeenix.routes.OrdersRoutes
import retrofit2.Call

class OrdersProvider(val token: String) {
    private var ordersRoutes: OrdersRoutes? = null

    init {
        val api = ApiRoutes()
        ordersRoutes = api.getOrdersRoutes(token)
    }

    fun create(order: Order): Call<ResponseHttp>? {
        return ordersRoutes?.create(order, token)
    }

}