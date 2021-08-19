package com.tms.projectapp

import android.app.Application
import com.tms.projectapp.database.DatabaseConstructor
import com.tms.projectapp.database.ScheduleDataBase
import com.tms.projectapp.repositories.ScheduleRepository
import com.tms.projectapp.viewModel.ScheduleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module



class MainApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(listOf(viewModels,repository,storageModule))
        }
    }

    private val viewModels = module {
        viewModel { ScheduleViewModel(get()) }
    }

    private val repository = module {
        factory{ ScheduleRepository(get()) }
    }

    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<ScheduleDataBase>().dataDao() }
    }



}