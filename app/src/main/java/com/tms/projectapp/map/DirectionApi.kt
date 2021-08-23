package com.tms.projectapp.map

import com.tms.projectapp.entities.DirectionResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionApi {

    @GET("/maps/api/directions/json")
    fun getDirection(
        @Query(value = "origin")  position:String,
        @Query(value = "destination") destination:String,
        @Query("key")  apiKey: String
    ): Call<DirectionResponce>

}