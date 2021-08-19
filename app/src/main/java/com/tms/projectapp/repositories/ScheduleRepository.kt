package com.tms.projectapp.repositories

import com.tms.projectapp.database.Data
import com.tms.projectapp.database.DataDao
import com.tms.projectapp.database.DataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ScheduleRepository(
    private val dataDao: DataDao
) {

    fun getData(): Flow<List<Data>> = dataDao.getData().map { dataEntity ->
        dataEntity.map { dataEntity ->
            Data(dataEntity.id, dataEntity.name, dataEntity.day, dataEntity.week, dataEntity.time)
        }
    }

    suspend fun insertData(data: DataEntity){
       withContext(Dispatchers.IO){
           dataDao.insertData(data)
       }
    }

    suspend fun deleteData(data: Data){
        withContext(Dispatchers.IO){
            dataDao.deleteData(data.entity())
        }

    }

    private fun Data.entity() = DataEntity(this.id, this.name,this.day,this.week,this.time)


}