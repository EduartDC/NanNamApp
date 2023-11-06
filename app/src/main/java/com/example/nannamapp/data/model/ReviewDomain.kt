package com.example.nannamapp.data.model

data class ReviewDomain (
    var idReview : String,
    var review : String,
    var rate : Int,
    var user_idUser : String,
    var recipe_idRecipe : String
)