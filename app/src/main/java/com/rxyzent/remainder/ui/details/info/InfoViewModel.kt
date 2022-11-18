package com.rxyzent.remainder.ui.details.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.ListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class InfoViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var db : MyDataBase

    val listLiveData = MutableLiveData<ListData>()

    fun loadData(id:Int){

        db.userDao().getDataById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ListData>(){
                override fun onSuccess(t: ListData) {
                    listLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun deleteData(it: ListData) {
        db.userDao().deleteData(it)
    }

    fun updateData(it: ListData) {
        db.userDao().updateData(it)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Int>(){
                override fun onSuccess(t: Int) {

                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

}