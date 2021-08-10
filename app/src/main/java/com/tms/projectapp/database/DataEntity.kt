package com.tms.projectapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "lesson_schedule")
data class DataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val day: String,
    val week: String,
    val time: String
)