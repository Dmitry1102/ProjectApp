package com.tms.projectapp.entities

import com.google.gson.annotations.SerializedName

data class Duration(
    @SerializedName("text") var text: String?,
    @SerializedName("value") var value: Int?
)