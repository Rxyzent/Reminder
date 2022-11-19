package com.rxyzent.remainder.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.NigthMode
import com.rxyzent.remainder.databinding.ActivityDetailsBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity() {

    private val binding by lazy {
        ActivityDetailsBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var db : MyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(getNightMode())

        val navController = Navigation.findNavController(this, R.id.details_nav_host_fragment)

        val flag = intent.getStringExtra("flag")

        if(flag == "edit"){
            navController.navigate(R.id.infoFragment)

        }else if(flag == "theme"){
            navController.navigate(R.id.themeFragment)
        }
    }
    fun getNightMode():Int{
        var nigthMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        db.userDao().getNightMode()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NigthMode>(){
                override fun onSuccess(t: NigthMode) {
                    nigthMode = t.mode
                }

                override fun onError(e: Throwable) {
                    nigthMode = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
                }

            })
        return nigthMode
    }
}