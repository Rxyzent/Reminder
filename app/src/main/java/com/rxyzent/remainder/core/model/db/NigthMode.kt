package com.rxyzent.remainder.core.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "night_mode")
class NigthMode (
    @ColumnInfo(name = "mode")
    var mode:Int
    ){
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "_id")
    var Id: Int? = 1
}