package com.rxyzent.remainder.ui.details.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.ListData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class EditViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var db : MyDataBase

    val listLiveData = MutableLiveData<ListData>()

    fun setData(data:ListData){
        listLiveData.postValue(data)
    }


    fun update(data: ListData) {
        db.userDao().updateData(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Int>(){
                override fun onSuccess(t: Int) {

                }

                override fun onError(e: Throwable) {

                }

            })
    }

}