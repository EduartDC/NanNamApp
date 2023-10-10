package com.example.namnam.data.model

import java.sql.Time

class Recipe (
    var idRecipe : String,
    var User_idUser : String,
    var receipName : String,
    var imageRecipeURL : String,
    var preparationTime : Time,
    var idMainIngredient : String,
)