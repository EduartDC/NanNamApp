package com.example.namnam.data.model

import java.sql.Time

class Recipe (
    var idRecipe : String,
    var user_idUser : String,
    var recipeName : String,
    var imageRecipeURL : String,
    var preparationTime : String,
    var idMainIngredient : String,
    var portion : String
)