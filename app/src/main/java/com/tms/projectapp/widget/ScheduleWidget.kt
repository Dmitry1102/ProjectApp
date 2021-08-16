package com.tms.projectapp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.text.format.Time
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import com.tms.projectapp.MainActivity
import com.tms.projectapp.R
import org.koin.core.component.KoinApiExtension
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
        remoteViews.setTextViewText(R.id.tv_title, intent?.getStringExtra(FOR_SCHEDULE))
        remoteViews.setTextViewText(R.id.tv_date, intent?.getStringExtra(FOR_SCHEDULE_TIME))
        remoteViews.setTextViewText(R.id.tv_day, intent?.getStringExtra(FOR_SCHEDULE_WEEK))
        remoteViews.setTextViewText(R.id.tv_week, intent?.getStringExtra(FOR_SCHEDULE_DAY))


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


    @KoinApiExtension
    private fun startSync(context: Context) {
        val intent = Intent(context, UpdateWidgetService::class.java)
        intent.action = APP_WIDGET_SYNC_ACTION
        JobIntentService.enqueueWork(context, UpdateWidgetService::class.java, JOB_ID, intent)
    }

    companion object{
        private val dateFormatter = SimpleDateFormat("HH:mm")

        const val APP_WIDGET_OPEN_APP = "PLANNER_APP_WIDGET_OPEN_APP"
        const val APP_WIDGET_SYNC_ACTION = "PLANNER_APP_WIDGET_SYNC_ACTION"
        const val APP_WIDGET_SYNC_RESULT = "PLANNER_APP_WIDGET_SYNC_RESULT"
        const val JOB_ID = 56555

        const val FOR_SCHEDULE = "FOR_SCHEDULE"
        const val FOR_SCHEDULE_TIME = "FOR_SCHEDULE_TIME"
        const val FOR_SCHEDULE_WEEK = "FOR_SCHEDULE_WEEK"
        const val FOR_SCHEDULE_DAY = "FOR_SCHEDULE_DAY"
    }

}