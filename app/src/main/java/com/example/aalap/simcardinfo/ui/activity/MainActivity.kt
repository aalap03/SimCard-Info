package com.example.aalap.simcardinfo.ui.activity

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.aalap.simcardinfo.ui.adapter.PagerAdapter
import com.example.aalap.simcardinfo.ui.fragments.PhoneInfoFrag
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.pref
import com.example.aalap.simcardinfo.ui.fragments.SIMFrag
import com.example.aalap.simcardinfo.utils.PdfUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(pref.getTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragments(SIMFrag())
        pagerAdapter.addFragments(PhoneInfoFrag())
        view_pager.adapter = pagerAdapter

//        info { PdfUtils().createPDFFile().absolutePath }
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.export_pdf -> {
                val pdfFile = PdfUtils().createPDFFile()
                PdfUtils().writeIntoPDFFile(pdfFile)
                Toast.makeText(this, "Created at ${pdfFile.absolutePath}", Toast.LENGTH_SHORT)
                        .show()
            }

            R.id.theme -> {
                pref.changeTheme()
                this.recreate()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            info { "LandScape" }
        } else
            info { "Portrait" }
    }
}
