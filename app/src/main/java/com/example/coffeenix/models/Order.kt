package com.example.coffeenix.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Order(
    @SerializedName("id") val id: String? = null,
    @SerializedName("id_client") var idClient: String,
    @SerializedName("id_delivery") var idDelivery: String? = null,
    @SerializedName("id_address") val idAddress: String,
    @SerializedName("status") val status: String? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("products") val products: ArrayList<Product>
) {

    override fun toString(): String {
        return "Order(id=$id, idClient='$idClient', idDelivery=$idDelivery, idAddress='$idAddress', status=$status, timestamp=$timestamp, products=$products)"
    }

    fun toJson(): String{
        return Gson().toJson(this)
    }
}