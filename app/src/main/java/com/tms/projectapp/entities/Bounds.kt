package com.tms.projectapp.entities

import com.google.gson.annotations.SerializedName

class Bounds(
    @SerializedName("northeast") var northeast: Northeast?,
    @SerializedName("southwest") var southwest: Southwest?


)