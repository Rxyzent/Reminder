package com.rxyzent.remainder.core.di.component

import android.app.Application
import com.rxyzent.remainder.core.di.module.app.AppModule
import com.rxyzent.remainder.core.di.module.dataBase.DaoModule
import com.rxyzent.remainder.core.di.module.dataBase.DbBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    DaoModule::class,
    DbBuilder::class
])
interface AppComponent: AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application):Builder

        fun build():AppComponent
    }

}