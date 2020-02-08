package com.sphtech.mobiledatausage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class DataUsage(

    @PrimaryKey
    @ColumnInfo(name = "_id")
    @SerializedName("_id")
    val id: Int,

    @ColumnInfo(name = "volume_of_mobile_data")
    @SerializedName("volume_of_mobile_data")
    val volume: String,

    @ColumnInfo(name = "quarter")
    @SerializedName("quarter")
    val quarter: String


) : Serializable
