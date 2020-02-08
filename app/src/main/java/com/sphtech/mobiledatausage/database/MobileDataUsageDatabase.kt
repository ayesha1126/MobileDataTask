package com.sphtech.mobiledatausage.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataUsage::class], version = 1, exportSchema = false)
abstract class MobileDataUsageDatabase: RoomDatabase() {

    abstract fun daoDataUsage(): DaoDataUsage

    companion object {

        private var mobileDataUsageDatabase: MobileDataUsageDatabase? = null

        fun getInstance(context: Context): MobileDataUsageDatabase? {

            synchronized(MobileDataUsageDatabase::class) {

                mobileDataUsageDatabase = Room.databaseBuilder(context,
                    MobileDataUsageDatabase::class.java,
                    "MobileDataUsage.db")
                    .allowMainThreadQueries()
                    .build()


            }

            return mobileDataUsageDatabase
        }

    }

}