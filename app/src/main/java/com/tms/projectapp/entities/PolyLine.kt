package com.tms.projectapp.entities

import com.google.gson.annotations.SerializedName

data class Polyline(
    @SerializedName("points") var points: String?
)