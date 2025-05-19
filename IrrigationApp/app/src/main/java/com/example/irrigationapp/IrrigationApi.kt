package com.example.irrigationapp

import retrofit2.Response
import retrofit2.http.*
data class IrrigationStatus(
    val moisture: Double,
    val watering: Boolean,
    val threshold: Double
)

data class ToggleResponse(
    val watering: Boolean
)
interface IrrigationApi {
    @GET("/")
    suspend fun getMoisture(): Response<String>

    @FormUrlEncoded
    @POST("/threshold")
    suspend fun setThreshold(@Field("threshold") threshold: String): Response<String>
    @GET("/api/status")
    suspend fun getStatus(): Response<IrrigationStatus>

    @POST("/api/toggle")
    suspend fun toggleWatering(): Response<ToggleResponse>
}