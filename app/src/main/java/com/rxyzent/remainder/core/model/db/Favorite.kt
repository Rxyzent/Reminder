package com.rxyzent.remainder.core.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @ColumnInfo(name="id")
    val id: Int,
    @ColumnInfo(name="title")
    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var Id: Int? = null
}