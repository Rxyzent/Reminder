package com.rxyzent.remainder.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.ListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor() :ViewModel() {

    @Inject
    lateinit var db : MyDataBase

    val listLiveData = MutableLiveData<List<ListData>>()

    fun loadAllData(){

        db.userDao().getDataByCompletable(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :DisposableSingleObserver<List<ListData>>(){
                override fun onSuccess(t: List<ListData>) {
                    listLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}