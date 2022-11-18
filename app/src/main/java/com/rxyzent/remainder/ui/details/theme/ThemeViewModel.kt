package com.rxyzent.remainder.ui.details.theme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.core.model.db.NigthMode
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ThemeViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var db : MyDataBase

    val listLiveData = MutableLiveData<NigthMode>()

    fun updateNightMode(mode: NigthMode){
        db.userDao().setNightMode(mode)
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

    fun setNightMode(mode: NigthMode){

        db.userDao().addNightMode(mode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Long>(){
                override fun onSuccess(t: Long) {

                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun getNightMode(){

        db.userDao().getNightMode()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NigthMode>(){
                override fun onSuccess(t: NigthMode) {
                    listLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}