package com.example.aalap.simcardinfo.ui.adapter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class PagerAdapter(var fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    var listFragment = mutableListOf<Fragment>()

    fun addFragments(fragment: Fragment) {
        listFragment.add(fragment)
    }

    override fun getItem(p0: Int): Fragment {
        return listFragment[p0]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> "SIM"

            1 -> "Phone"

            else -> "Nothing"
        }
    }
}
