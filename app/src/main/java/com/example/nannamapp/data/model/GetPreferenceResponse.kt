package com.example.nannamapp.data.model

import com.example.namnam.data.model.Category

data class GetPreferenceResponse (
    var userpreferences : List<Category> = emptyList(),
    var categories : List<Category> = emptyList()
)