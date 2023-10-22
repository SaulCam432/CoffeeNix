package com.example.coffeenix.models

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("image") val image: String? = null,
    @SerializedName("password") val password: String,
    @SerializedName("is_available") val isAvailable: Boolean? = null,
    @SerializedName("session_token") val sessionToken: String? = null,
    @SerializedName("roles") val roles : ArrayList<Rol>? = null
) {
    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email', phone='$phone', image=$image, password='$password', isAvailable=$isAvailable, sessionToken=$sessionToken, roles=$roles)"
    }
}