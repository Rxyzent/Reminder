package com.rxyzent.remainder.core.helper

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.MyEvent
import com.rxyzent.remainder.core.model.db.ListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import javax.inject.Inject

class WidgetViewsFactory(ctxt: Context?, intent: Intent) :
    RemoteViewsFactory {
    private var ctxt: Context? = null
    private val appWidgetId: Int
    private lateinit var data:List<ListData>
//  private val currentTime = System.currentTimeMillis()


    init {
        this.ctxt = ctxt
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID)
    }

    override fun onCreate() {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        // no-op
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    fun onWidgetEvent(event: List<ListData>) {
        val stickyEvent = EventBus.getDefault().removeStickyEvent(MyEvent::class.java)
        stickyEvent?.let {
            data = it.data
            Log.d("OnMessageEvent", "onReciveEvent: ${it.data}")
        }
    }

    override fun onDestroy() {
        // no-op
        EventBus.getDefault().unregister(this)
    }

    override fun getCount(): Int {
        return items.size
    }

    @SuppressLint("RemoteViewLayout")
    override fun getViewAt(position: Int): RemoteViews {
        var icon = R.drawable.ic_baseline_sticky_note_2_24
//        val days = (data[position].date-currentTime)/(1000*60*60*24).toInt()
//        val dayStr = "D+".plus(days)
        when(items[position].type){
            "todo"->icon = R.drawable.ic_baseline_sticky_note_2_24
            "payment"->icon = R.drawable.ic_baseline_monetization_on_24
            "birthday"->icon = R.drawable.ic_baseline_card_giftcard_24
        }
        val row = RemoteViews(ctxt!!.packageName,
            R.layout.widget_list_item)
        row.setTextViewText(R.id.widget_title, items[position].title)
        row.setTextViewText(R.id.widget_description, items[position].description)
        //row.setTextViewText(R.id.widget_days,dayStr)
        row.setImageViewResource(R.id.widget_icon_back,icon)
        val i = Intent()
        val extras = Bundle()
        extras.putString(WidgetProvider.EXTRA_WORD, items[position].title)
        i.putExtras(extras)
        row.setOnClickFillInIntent(R.id.widget_title, i)
        return row
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        // no-op
    }
companion object{
    private val items = arrayOf(ListData("title1","description1",System.currentTimeMillis(),"birthday",false),
        ListData("title2","description2",System.currentTimeMillis(),"todo",false),
        ListData("title3","description3",System.currentTimeMillis(),"payment",false),
        ListData("title4","description4",System.currentTimeMillis(),"birthday",false),
        ListData("title5","description5",System.currentTimeMillis(),"todo",false))
}

}