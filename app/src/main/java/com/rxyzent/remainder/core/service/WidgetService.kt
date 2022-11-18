package com.rxyzent.remainder.core.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.rxyzent.remainder.core.helper.WidgetViewsFactory


class WidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return WidgetViewsFactory(this.applicationContext,
            intent)
    }
}