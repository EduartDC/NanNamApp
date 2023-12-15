package com.example.nannamapp.data.model

import com.google.gson.annotations.SerializedName

data class JsonResult (
    @SerializedName("jwtToken") val jwtToken: String,
    @SerializedName("idUser") val idUser: String
)