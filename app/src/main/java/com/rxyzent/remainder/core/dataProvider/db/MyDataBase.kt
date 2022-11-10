package com.rxyzent.remainder.core.dataProvider.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rxyzent.remainder.core.model.db.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class MyDataBase : RoomDatabase(){

    abstract fun userDao(): DbDao

}