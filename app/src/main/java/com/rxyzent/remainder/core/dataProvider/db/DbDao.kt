package com.rxyzent.remainder.core.dataProvider.db

import androidx.room.*
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.core.model.db.NigthMode
import io.reactivex.rxjava3.core.Single

@Dao
interface DbDao {

    @Query("SELECT * FROM todo_table")
    fun getAllData(): Single<List<ListData>>

    @Query("select * from todo_table where type = :type")
    fun getData(type :String) :Single<List<ListData>>

    @Query("select * from todo_table where _id = :id")
    fun getDataById(id:Int) : Single<ListData>

    @Query("select * from todo_table where isCompleted = :isCompleted")
    fun getDataByCompletable(isCompleted :Boolean):Single<List<ListData>>

    @Insert(entity = ListData::class, onConflict = OnConflictStrategy.REPLACE)
    fun addData(data: ListData) : Single<Long>

    @Delete(entity = ListData::class)
    fun deleteData(data: ListData)

    @Update(entity = ListData::class , onConflict = OnConflictStrategy.REPLACE)
    fun updateData(data: ListData):Single<Int>

    @Insert(entity = NigthMode::class, onConflict = OnConflictStrategy.REPLACE)
    fun addNightMode(mode: NigthMode) :Single<Long>

    @Query("select * from night_mode")
    fun getNightMode() :Single<NigthMode>

    @Update(entity = NigthMode::class, onConflict = OnConflictStrategy.REPLACE)
    fun setNightMode(mode:NigthMode):Single<Int>

}