package com.example.coffeenix.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Category(
    val id: String? = null,
    val name: String,
    val image: String? = null
) {
    override fun toString(): String {
        return name
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}