package com.tms.projectapp.schedule

import com.tms.projectapp.MainActivity
import com.tms.projectapp.database.Data
import com.tms.projectapp.database.DataDao
import com.tms.projectapp.database.DataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class ScheduleRepository(
    private val dataDao: DataDao
) {

    fun getData(): Flow<List<Data>> = dataDao.getData().map { dataEntity ->
        dataEntity.map { dataEntity ->
            Data(dataEntity.id, dataEntity.name, dataEntity.day, dataEntity.week, dataEntity.time)
        }
    }

    suspend fun insertData(data: DataEntity){
        dataDao.insertData(data)
    }

    suspend fun deleteData(data: Data){
        dataDao.deleteData(data.entity())
    }

    private fun Data.entity() = DataEntity(this.id, this.name,this.day,this.week,this.time)


}