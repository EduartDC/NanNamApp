package com.example.namnam.data.model

import java.io.Serializable
import android.os.Parcel
import android.os.Parcelable

class Ingredient(
    var idIngredient: String,
    var ingredientname: String,
    var measure: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idIngredient)
        parcel.writeString(ingredientname)
        parcel.writeString(measure)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ingredient> {
        override fun createFromParcel(parcel: Parcel): Ingredient {
            return Ingredient(parcel)
        }

        override fun newArray(size: Int): Array<Ingredient?> {
            return arrayOfNulls(size)
        }
    }
}