package com.tms.projectapp.entities

import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points") var points: String?
)