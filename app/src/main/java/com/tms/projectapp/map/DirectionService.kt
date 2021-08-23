package com.tms.projectapp.map

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DirectionService {

    const val URL = "https://maps.googleapis.com"

    fun apiService(): DirectionApi{

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL)
            .build()

        return retrofit.create(DirectionApi::class.java)
    }

}