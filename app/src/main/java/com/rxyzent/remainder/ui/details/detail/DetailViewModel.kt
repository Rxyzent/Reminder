package com.rxyzent.remainder.ui.details.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.ListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor() :ViewModel() {

    @Inject
    lateinit var db : MyDataBase

    val listLiveData = MutableLiveData<ListData>()

    fun addData(todo:ListData){

        db.userDao().addData(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<Long>(){
                override fun onSuccess(t: Long) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

}