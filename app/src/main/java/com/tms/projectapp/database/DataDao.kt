package com.tms.projectapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {

    @Query("SELECT * FROM lesson_schedule")
    fun getData(): Flow<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(data: DataEntity)

    @Delete
    fun deleteData(data: DataEntity)

}