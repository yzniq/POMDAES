package com.example.irrigationapp

import retrofit2.Response
import retrofit2.http.*

interface IrrigationApi {
    @GET("/")
    suspend fun getMoisture(): Response<String>

    @FormUrlEncoded
    @POST("/threshold")
    suspend fun setThreshold(@Field("threshold") threshold: String): Response<String>
}