package com.tms.projectapp.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tms.projectapp.database.Data
import com.tms.projectapp.database.DataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
): ViewModel(), KoinComponent {

    val liveData: LiveData<List<Data>> = scheduleRepository.getData().asLiveData()

    fun addToDataBase(name: String, day: String, week: String, time: String){
        val schedule = DataEntity(
            name = name,
            day = day,
            week = week,
            time = time
        )

        viewModelScope.launch(Dispatchers.IO) {
            scheduleRepository.insertData(schedule)
        }
    }

    fun deleteFromDataBase(data: Data){
        viewModelScope.launch(Dispatchers.IO) {
            scheduleRepository.deleteData(data)
        }
    }

}