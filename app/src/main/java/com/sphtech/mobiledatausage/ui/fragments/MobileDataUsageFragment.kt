package com.sphtech.mobiledatausage.ui.fragments


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration

import com.sphtech.mobiledatausage.R
import com.sphtech.mobiledatausage.database.DataUsage
import com.sphtech.mobiledatausage.repository.DataLoader
import com.sphtech.mobiledatausage.ui.adapters.DataUsageAdapter
import com.sphtech.mobiledatausage.ui.viewModel.DataUsageViewModel
import kotlinx.android.synthetic.main.fragment_mobile_data_usage.*

class MobileDataUsageFragment : Fragment() {

    private lateinit var viewModel: DataUsageViewModel


    private val onDataItemClickListener = object : DataUsageAdapter.OnDataItemClickListener {

        override fun onImageClicked(dataUsage: DataUsage, isDecrease: Boolean) {

            val msg = if (isDecrease) {
                "Data usage decreased in ${dataUsage.quarter}"
            }else {
                "Data usage increased in ${dataUsage.quarter}"
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        override fun onItemClicked(dataUsage: DataUsage) {
            Toast.makeText(context, "Data usage in ${dataUsage.quarter}: ${dataUsage.volume}", Toast.LENGTH_SHORT).show()
        }

    }

    private val apiCallStatusListener = object : DataLoader.OnApiCallStatusListener {

        override fun onSuccess() {
            swipeRefresh.isRefreshing = false
            Toast.makeText(context,"Records updated successfully!", Toast.LENGTH_SHORT).show()
        }

        override fun onOffline() {
            viewModel.loadFromDb()
            swipeRefresh.isRefreshing = false
            Toast.makeText(context,"No Internet Access!", Toast.LENGTH_SHORT).show()
        }

        override fun onError(msg: String) {
            swipeRefresh.isRefreshing = false
            viewModel.loadFromDb()
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(DataUsageViewModel::class.java)

        return inflater.inflate(R.layout.fragment_mobile_data_usage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        if (savedInstanceState == null) {
            loadData()
        }

    }

    private fun init() {
        rvList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        swipeRefresh.setOnRefreshListener {
            viewModel.loadData()
        }

        viewModel.init(apiCallStatusListener)

        viewModel.mList.observe(viewLifecycleOwner, Observer {

            if (it != null && it.isNotEmpty()) {

                if (tvNoData.visibility == View.VISIBLE) {
                    tvNoData.visibility = View.GONE
                    return@Observer
                }

                val adapter = DataUsageAdapter(it, onDataItemClickListener)
                rvList.adapter = adapter

                viewModel.saveData()

                return@Observer
            }

            tvNoData.visibility = View.VISIBLE

        })
    }

    private fun loadData() {

        swipeRefresh.isRefreshing = true

        viewModel.loadData()

    }

}
