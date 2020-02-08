package com.sphtech.mobiledatausage

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sphtech.mobiledatausage.database.MobileDataUsageDatabase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var appContext: Context

    @Before
    fun getAppContext() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun useAppContext() {

        val mobileDataUsageDatabase = MobileDataUsageDatabase.getInstance(appContext)

        val list = mobileDataUsageDatabase?.daoDataUsage()?.fetchAllRecords()

        assertTrue("Records are saved in database",list!!.isNotEmpty())

    }

}
