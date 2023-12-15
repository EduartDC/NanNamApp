package com.example.nannamapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("contentType") val contentType: String?,
    @SerializedName("serializerSettings") val serializerSettings: String?,
    @SerializedName("statusCode") val statusCode: String?,
    @SerializedName("value") val value: JsonResult?
)

data class JsonResult (
    @SerializedName("jwtToken") val jwtToken: String,
    @SerializedName("idUser") val idUser: String
)
