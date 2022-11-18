package com.rxyzent.remainder.core.helper

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.service.WidgetService
import com.rxyzent.remainder.ui.main.MainActivity
import java.text.SimpleDateFormat

class WidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        ctxt: Context, appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        for (i in appWidgetIds.indices) {
            val simpleDateFormat = SimpleDateFormat("E")
            val simpleDateFormat2 = SimpleDateFormat("dd MMM yyyy")
            val currentTime = System.currentTimeMillis()
            val dayOfWeek = simpleDateFormat.format(currentTime)
            val date = simpleDateFormat2.format(currentTime)

            val svcIntent = Intent(ctxt, WidgetService::class.java)
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i])
            svcIntent.data = Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME))
            val widget = RemoteViews(ctxt.packageName,
                R.layout.widget)
            widget.setTextViewText(R.id.widgetDayOfWeek,dayOfWeek)
            widget.setTextViewText(R.id.widgetDate,date)
            widget.setRemoteAdapter(appWidgetIds[i], R.id.words,
                svcIntent)
            val clickIntent = Intent(ctxt, MainActivity::class.java)
            val clickPI = PendingIntent
                .getActivity(ctxt, 0,
                    clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
            widget.setPendingIntentTemplate(R.id.words, clickPI)
            appWidgetManager.updateAppWidget(appWidgetIds[i], widget)
        }
        super.onUpdate(ctxt, appWidgetManager, appWidgetIds)
    }

    companion object {
        var EXTRA_WORD = "com.commonsware.android.appwidget.lorem.WORD"
    }
}