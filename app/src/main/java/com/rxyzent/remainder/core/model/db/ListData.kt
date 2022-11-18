package com.rxyzent.remainder.core.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ListData(
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="description")
    var description: String,
    @ColumnInfo(name="date")
    var date: Long,
    @ColumnInfo(name="type")
    var type: String,
    @ColumnInfo(name = "isCompleted")
    var isCompleted:Boolean
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var todoId: Int? = null
}