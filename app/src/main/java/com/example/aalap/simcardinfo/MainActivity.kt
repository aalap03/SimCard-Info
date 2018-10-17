package com.example.aalap.simcardinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
