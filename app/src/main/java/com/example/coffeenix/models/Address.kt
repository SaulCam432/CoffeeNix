package com.example.coffeenix.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Address (
    @SerializedName("id") val id: String? = null,
    @SerializedName("id_user") val idUser: String,
    @SerializedName("address") val address: String,
    @SerializedName("town") val town: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
) {

    override fun toString(): String {
        return "Address(id=$id, idUser='$idUser', address='$address', town='$town', lat=$lat, lng=$lng)"
    }

    fun toJson(): String{
        return Gson().toJson(this)
    }
}