package com.example.urlsapp.backendapi

import com.example.urlsapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface DjangoApi {
    @POST("/api/token/")
    fun  getToken(
        @Body info: LogInBody
    ): Call<Token>
}