package com.rxyzent.remainder.core.dataProvider.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.core.model.db.NigthMode

@Database(entities = [ListData::class,NigthMode::class], version = 2)
abstract class MyDataBase : RoomDatabase(){

    abstract fun userDao(): DbDao

}