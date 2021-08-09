package com.tms.projectapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 1)
abstract class ScheduleDataBase: RoomDatabase()  {

    abstract fun dataDao():DataDao

}

object DatabaseConstructor {
    fun create(context: Context): ScheduleDataBase =
        Room.databaseBuilder(
            context,
            ScheduleDataBase::class.java,
            "lesson_schedule"
        ).build()
}