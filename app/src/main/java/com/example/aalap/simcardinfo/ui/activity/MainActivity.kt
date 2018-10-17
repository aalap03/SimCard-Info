package com.example.aalap.simcardinfo.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.aalap.simcardinfo.ui.adapter.PagerAdapter
import com.example.aalap.simcardinfo.ui.fragments.PhoneInfoFrag
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.ui.fragments.SIMFrag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragments(SIMFrag())
        pagerAdapter.addFragments(PhoneInfoFrag())
        view_pager.adapter = pagerAdapter

        tab_layout.setupWithViewPager(view_pager)
    }
}
