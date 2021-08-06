package com.tms.projectapp

import android.app.Application
import com.tms.projectapp.schedule.ScheduleRepository
import com.tms.projectapp.schedule.ScheduleViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module


@KoinApiExtension
class MainApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(listOf(viewModels,repository))
        }
    }

    private val viewModels = module {
        viewModel { ScheduleViewModel(get()) }
    }

    private val repository = module {
        factory { ScheduleRepository(get()) }

    }



}