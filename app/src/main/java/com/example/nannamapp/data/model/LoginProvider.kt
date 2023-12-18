package com.example.nannamapp.data.model

class LoginProvider {
    companion object{
        var login : JsonResult? = ApiResponse(null, null, null, null).value
        var user: User = User("","","","","")

    }
}