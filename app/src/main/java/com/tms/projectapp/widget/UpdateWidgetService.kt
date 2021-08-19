package com.tms.projectapp.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent


@KoinApiExtension
class UpdateWidgetService: JobIntentService(), KoinComponent {


    override fun onHandleWork(intent: Intent) {
        val name = intent.getStringExtra(NAME_LESSON)
        val day  = intent.getStringExtra(DAY_LESSON)
        val time = intent.getStringExtra(TIME_LESSON)
        val week = intent.getStringExtra(WEEK_LESSON)
        updateWidget(name.toString(), time.toString(), day.toString(), week.toString())

    }

    private fun updateWidget(name: String, time: String, day:String , week: String) {
        val intent = Intent(applicationContext, ScheduleWidget::class.java)
        intent.action = ScheduleWidget.APP_WIDGET_SYNC_RESULT

        intent.putExtra(ScheduleWidget.FOR_SCHEDULE, name)
        intent.putExtra(ScheduleWidget.FOR_SCHEDULE_DAY,day)
        intent.putExtra(ScheduleWidget.FOR_SCHEDULE_TIME, time)
        intent.putExtra(ScheduleWidget.FOR_SCHEDULE_WEEK,week)


        val widgetManager = AppWidgetManager.getInstance(applicationContext)
        val ids = widgetManager.getAppWidgetIds(
            ComponentName(
                applicationContext,
                ScheduleWidget::class.java
            )
        )
        widgetManager.notifyAppWidgetViewDataChanged(ids, android.R.id.list)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        applicationContext.sendBroadcast(intent)
    }

    companion object{
        const val UPDATE_ID = 123
        const val DATA_SYNC = "DATA_SYNC"

        const val NAME_LESSON = "NAME_LESSON"
        const val TIME_LESSON = "TIME_LESSON"
        const val WEEK_LESSON = "WEEK_LESSON"
        const val DAY_LESSON = "DAY_LESSON"

    }
}

