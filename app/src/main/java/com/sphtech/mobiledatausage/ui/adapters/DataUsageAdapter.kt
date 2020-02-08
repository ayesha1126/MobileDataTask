package com.sphtech.mobiledatausage.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sphtech.mobiledatausage.R
import com.sphtech.mobiledatausage.database.DataUsage
import java.lang.Exception

class DataUsageAdapter(private val list: List<DataUsage>, private val onDataItemClickListener: OnDataItemClickListener): RecyclerView.Adapter<DataUsageAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_usage, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = list[position]

        holder.tvQuarter.text = data.quarter
        holder.tvVolume.text = data.volume

        holder.itemView.setOnClickListener {
            onDataItemClickListener.onItemClicked(data)
        }

        var currentUsage = 0.0
        try {
            currentUsage = data.volume.toDouble()
        }catch (e: Exception) {
            e.printStackTrace()
        }

        var prevUsage = 0.0

        if (position > 0) {
            try {
                prevUsage = list[position-1].volume.toDouble()
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }


        val decrease: Boolean
        if (prevUsage > currentUsage ) {
            decrease = true
            holder.imgIcon.setImageResource(R.drawable.ic_chevron_down)
        }else {
            decrease = false
            holder.imgIcon.setImageResource(R.drawable.ic_chevron_up)
        }

        holder.imgIcon.setOnClickListener {
            onDataItemClickListener.onImageClicked(data, decrease )
        }

    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvQuarter: TextView = view.findViewById(R.id.tvQuarter)
        val tvVolume: TextView = view.findViewById(R.id.tvVolume)
        val imgIcon: ImageView = view.findViewById(R.id.imgIcon)

    }

    interface OnDataItemClickListener {

        fun onItemClicked(dataUsage: DataUsage)
        fun onImageClicked(dataUsage: DataUsage, isDecrease: Boolean)

    }

}