package com.sphtech.mobiledatausage.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.sphtech.mobiledatausage.config.Constants
import com.sphtech.mobiledatausage.database.DataUsage
import org.json.JSONObject
import java.lang.Exception
import android.net.ConnectivityManager
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

class DataLoader (private val context: Context,
                  private val mList: MutableLiveData<List<DataUsage>>,
                  private val onApiCallStatusListener: OnApiCallStatusListener) {

    private val tag = DataLoader::class.java.simpleName

    fun loadData() {

        if (!isInternetAvailable()) {
            onApiCallStatusListener.onOffline()

            return
        }

        Ion.with(context)
            .load(Constants.API_URL)
            .asString()
            .setCallback { e, result ->

                if (e != null) {
                    onApiCallStatusListener.onError("Failed to load data\n${e.message}")
                    e.printStackTrace()
                    return@setCallback
                }

                try {

                    Log.e(tag, "response: $result")

                    val res = JSONObject(result)

                    if (res.getBoolean("success")) {

                        val data = res.getJSONObject("result")

                        val records = data.getJSONArray("records")

                        val typeToken = object : TypeToken<ArrayList<DataUsage>>() {}.type
                        val list: ArrayList<DataUsage> = Gson().fromJson(records.toString(), typeToken)

                        mList.postValue(list)

                        onApiCallStatusListener.onSuccess()

                        return@setCallback
                    }

                    val error = if (res.has("message")) {
                        res.getString("message")
                    }else {
                        "Data not found for resource id"
                    }

                    onApiCallStatusListener.onError(error)

                    return@setCallback

                }catch (e: Exception) {
                    e.printStackTrace()
                    onApiCallStatusListener.onError("Error Occurred!")
                }

            }

    }

    private fun isInternetAvailable(): Boolean{

        var isConnected = false
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            isConnected = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                activeNetworkInfo?.run {
                    isConnected = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }

        return isConnected
    }


    interface OnApiCallStatusListener{

        fun onSuccess()

        fun onOffline()

        fun onError(msg: String)

    }

}