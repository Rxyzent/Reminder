package com.rxyzent.remainder.ui.main

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import com.rxyzent.remainder.core.model.db.NigthMode
import com.rxyzent.remainder.core.service.MyService
import com.rxyzent.remainder.databinding.ActivityMainBinding
import com.rxyzent.remainder.ui.details.DetailsActivity
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    protected var modeOrder = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    @Inject
    lateinit var db : MyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermissionOrStart()
        canExactAlarmsBeScheduled()

        if(getNightMode()!=0) {
            modeOrder =  getNightMode()
        }


        AppCompatDelegate.setDefaultNightMode(modeOrder)

        val navController = Navigation.findNavController(this,R.id.main_nav_host_fragment)

        binding.category.setOnClickListener {
            val popup = PopupMenu(this@MainActivity, it)
            popup.inflate(R.menu.popup_menu)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                when (item!!.itemId) {
                    R.id.action_all -> {
                        navController.navigate(R.id.homeFragment)
                        binding.categoryStr.text = "Active"
                    }
                    R.id.action_todo -> {
                        navController.navigate(R.id.todoFragment)
                        binding.categoryStr.text = item.title
                    }
                    R.id.action_birthday -> {
                        navController.navigate(R.id.birthdayFragment)
                        binding.categoryStr.text = item.title
                    }
                    R.id.action_payment -> {
                        navController.navigate(R.id.paymentFragment)
                        binding.categoryStr.text = item.title
                    }
                }

                true
            })

            popup.show()
        }

        binding.options.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }


        binding.addBtn.setOnClickListener {
            intent = Intent(this@MainActivity,DetailsActivity::class.java)
            intent.putExtra("flag","detail")
            startActivity(intent)
        }


        binding.navView.setNavigationItemSelectedListener(object :NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.nav_home ->{
                        navController.navigate(R.id.homeFragment)
                        binding.categoryStr.text = "Active"
                    }
                    R.id.nav_complete->{
                        navController.navigate(R.id.completedFragment)
                        binding.categoryStr.text = "Completed"
                    }
                    R.id.nav_theme->{
                        val intent = Intent(this@MainActivity,DetailsActivity::class.java)
                        intent.putExtra("flag","theme")
                        startActivity(intent)
                    }
                    else -> {
                        Toast.makeText(this@MainActivity, "In developing ...", Toast.LENGTH_SHORT).show()
                    }
                }

                binding.drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }

        })

        binding.navView.bringToFront()

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
                    TODO("Not yet implemented")
                }

            })
        return nigthMode
    }
    fun setDefaulMode(){
        val mode = NigthMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            db.userDao().addNightMode(mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Long>(){
                    override fun onSuccess(t: Long) {

                    }

                    override fun onError(e: Throwable) {

                    }

                })
    }

    private fun checkPermissionOrStart() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {

            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {

            }
            else -> {

                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }


    private fun canExactAlarmsBeScheduled(): Boolean {
        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true // below API it can always be scheduled
        }
    }
}