package com.tms.projectapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import com.tms.projectapp.MainActivity
import com.tms.projectapp.R
import java.text.SimpleDateFormat
import java.util.*

class ScheduleWidget: AppWidgetProvider() {


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val remoteViews = RemoteViews(context.packageName, R.layout.shedule_widget)

        remoteViews.setOnClickPendingIntent(R.id.ivSync, getSyncRequestIntent(context) )
        remoteViews.setOnClickPendingIntent(R.id.widgetFrame, getStartActivityIntent(context))

        appWidgetManager?.updateAppWidget(appWidgetIds, remoteViews)

        startSync(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val remoteViews = RemoteViews(context.packageName, R.layout.shedule_widget)
        val componentWidget = ComponentName(context, ScheduleWidget::class.java)

        when (intent.action) {
            APP_WIDGET_SYNC_ACTION -> handleSyncRequest(context, remoteViews)
            APP_WIDGET_SYNC_RESULT -> handleSyncResult(remoteViews, intent)
            APP_WIDGET_OPEN_APP -> {
                context.startActivity(Intent(context, MainActivity::class.java))
            }
        }

        val ids = appWidgetManager.getAppWidgetIds(componentWidget)

        remoteViews.setOnClickPendingIntent(R.id.ivSync, getSyncRequestIntent(context))
        remoteViews.setOnClickPendingIntent(R.id.widgetFrame, getStartActivityIntent(context))

        remoteViews.setOnClickPendingIntent(
            R.id.widgetFrame,
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        )

        appWidgetManager.updateAppWidget(ids, remoteViews)

    }

    private fun handleSyncRequest(context: Context, remoteViews: RemoteViews) {
        startSync(context)
    }

    private fun handleSyncResult(remoteViews: RemoteViews, intent: Intent?) {
        remoteViews.setTextViewText(R.id.tv_title, intent?.getStringExtra(KEY_SCHEDULE_NAME))
        val time: Long = intent?.getLongExtra(KEY_SCHEDULE_TIME, 0L) ?: 0L
        if (time > 0) {
            remoteViews.setTextViewText(R.id.tv_date, dateFormatter.format(Date(time)))
        } else {
            remoteViews.setTextViewText(R.id.tv_date, "")
        }
        remoteViews.setTextViewText(R.id.tv_day, intent?.getStringExtra(KEY_SCHEDULE_DAY))
        remoteViews.setTextViewText(R.id.tv_week, intent?.getStringExtra(KEY_SCHEDULE_WEEK))

    }







    private fun getSyncRequestIntent(context: Context?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = APP_WIDGET_SYNC_ACTION
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun getStartActivityIntent(context: Context?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = APP_WIDGET_OPEN_APP
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun startSync(context: Context) {
        val intent = Intent(context, UpdateWidgetService::class.java)
        intent.action = APP_WIDGET_SYNC_ACTION
        JobIntentService.enqueueWork(context, UpdateWidgetService::class.java, JOB_ID, intent)
    }

    companion object{
        private val dateFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        const val APP_WIDGET_OPEN_APP = "PLANNER_APP_WIDGET_OPEN_APP"
        const val APP_WIDGET_SYNC_ACTION = "PLANNER_APP_WIDGET_SYNC_ACTION"
        const val APP_WIDGET_SYNC_RESULT = "PLANNER_APP_WIDGET_SYNC_RESULT"
        const val JOB_ID = 14839

        const val KEY_SCHEDULE_NAME = "APP_SCHEDULE_TEXT"
        const val KEY_SCHEDULE_TIME = "KEY_SCHEDULE_TIME"
        const val KEY_SCHEDULE_WEEK = "KEY_SCHEDULE_WEEK"
        const val KEY_SCHEDULE_DAY = "KEY_SCHEDULE_DAY"
    }

}