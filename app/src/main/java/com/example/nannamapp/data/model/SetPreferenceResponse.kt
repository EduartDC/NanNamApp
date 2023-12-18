package com.example.nannamapp.data.model

import com.example.namnam.data.model.Category

data class SetPreferenceResponse (
    var idUser : String = "",
    var userPreference : List<String> = emptyList()
)