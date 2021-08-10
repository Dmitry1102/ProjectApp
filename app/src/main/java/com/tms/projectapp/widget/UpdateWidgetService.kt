package com.tms.projectapp.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.core.app.JobIntentService
import com.tms.projectapp.schedule.AddFragment
import com.tms.projectapp.schedule.AddFragment.Companion.DAY_LESSON
import com.tms.projectapp.schedule.AddFragment.Companion.NAME_LESSON
import com.tms.projectapp.schedule.AddFragment.Companion.TIME_LESSON
import com.tms.projectapp.schedule.AddFragment.Companion.WEEK_LESSON
import com.tms.projectapp.schedule.ScheduleRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


@KoinApiExtension
class UpdateWidgetService: JobIntentService(), KoinComponent {


    override fun onHandleWork(intent: Intent) {
        val name = intent.getStringExtra(NAME_LESSON)
        val day  = intent.getStringExtra(DAY_LESSON)
        val time = intent.getStringExtra(TIME_LESSON)
        val week = intent.getStringExtra(WEEK_LESSON)
        if (time != null) {
            updateWidget(name.toString(), time, day.toString(), week.toString())
        }
    }

    private fun updateWidget(name: String, time: String, day:String , week: String) {
        val intent = Intent(applicationContext, ScheduleWidget::class.java)
        intent.action = ScheduleWidget.APP_WIDGET_SYNC_RESULT

        intent.putExtra(ScheduleWidget.KEY_SCHEDULE_NAME, name)
        intent.putExtra(ScheduleWidget.KEY_SCHEDULE_TIME, time)
        intent.putExtra(ScheduleWidget.KEY_SCHEDULE_DAY,day)
        intent.putExtra(ScheduleWidget.KEY_SCHEDULE_WEEK,week)


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
}

