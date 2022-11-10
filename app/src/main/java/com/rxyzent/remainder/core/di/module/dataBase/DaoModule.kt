package com.rxyzent.remainder.core.di.module.dataBase

import com.rxyzent.remainder.core.dataProvider.db.DbDao
import com.rxyzent.remainder.core.dataProvider.db.MyDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideDao(db: MyDataBase): DbDao {
        return db.userDao()
    }
}