package com.sphtech.mobiledatausage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.lifecycle.LiveData
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DaoDataUsage {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData (list: List<DataUsage>)

    @Query("SELECT * FROM DataUsage ORDER BY _id desc")
    fun fetchAllRecords(): List<DataUsage>

    @Query("DELETE FROM DataUsage")
    fun deleteAllRecords()

}