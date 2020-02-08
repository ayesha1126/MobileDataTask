package com.sphtech.mobiledatausage.ui.viewModel

import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sphtech.mobiledatausage.database.DataUsage
import com.sphtech.mobiledatausage.database.MobileDataUsageDatabase
import com.sphtech.mobiledatausage.repository.DataLoader

class DataUsageViewModel(application: Application) : AndroidViewModel(application) {

    private var mobileDataUsageDatabase = MobileDataUsageDatabase.getInstance(application)

    val mList = MutableLiveData<List<DataUsage>>()

    private val workerThread: Handler = Handler()
    private lateinit var dataLoader: DataLoader


    fun init(
        apiCallStatusListener: DataLoader.OnApiCallStatusListener) {
        this.dataLoader = DataLoader(getApplication(), mList, apiCallStatusListener)
    }

    fun loadData() {

        workerThread.post {
            dataLoader.loadData()
        }

    }

    fun saveData() {
        workerThread.post {
            mobileDataUsageDatabase?.daoDataUsage()?.deleteAllRecords()

            val list = mList.value!!

            mobileDataUsageDatabase?.daoDataUsage()?.insertData(list)
        }
    }

    fun loadFromDb() {
        workerThread.post {

            val list = mobileDataUsageDatabase?.daoDataUsage()?.fetchAllRecords()

            Log.e("Offline","List from db")

            mList.postValue(list)
        }
    }

}