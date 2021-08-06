package com.tms.projectapp.database

import android.os.Parcel
import android.os.Parcelable
import androidx.room.PrimaryKey

data class Data(
    val id : Long,
    val name: String,
    val day: String,
    val week: String,
    val time: Long
)