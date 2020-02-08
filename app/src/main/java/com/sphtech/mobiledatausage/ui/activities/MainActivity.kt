package com.sphtech.mobiledatausage.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sphtech.mobiledatausage.R
import com.sphtech.mobiledatausage.ui.fragments.MobileDataUsageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragmentView, MobileDataUsageFragment(), MobileDataUsageFragment::class.java.simpleName)
            ft.commit()
        }


    }
}
