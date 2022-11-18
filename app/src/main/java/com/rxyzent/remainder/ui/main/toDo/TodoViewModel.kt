package com.rxyzent.remainder.ui.main.toDo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.ListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TodoViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db : MyDataBase

    val listLiveData = MutableLiveData<List<ListData>>()

    fun loadAllData(){

        db.userDao().getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<ListData>>(){
                override fun onSuccess(t: List<ListData>) {
                    listLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun loadData(type:String){

        db.userDao().getData(type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<ListData>>(){
                override fun onSuccess(t: List<ListData>) {
                    listLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}