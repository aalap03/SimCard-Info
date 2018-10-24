package com.example.aalap.simcardinfo.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.aalap.simcardinfo.App
import com.example.aalap.simcardinfo.ui.adapter.PagerAdapter
import com.example.aalap.simcardinfo.ui.fragments.PhoneInfoFrag
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.ui.fragments.SIMFrag
import com.example.aalap.simcardinfo.utils.FontUtils
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), AnkoLogger {

    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragments(SIMFrag())
        pagerAdapter.addFragments(PhoneInfoFrag())
        view_pager.adapter = pagerAdapter

        tab_layout.setupWithViewPager(view_pager)

//        FontUtils().registerFont()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.export_pdf -> {

                thread {
                    val doc = Document()

                    try {
                        val path = Environment.getExternalStorageDirectory().absolutePath + "/Dir"

                        val dir = File(path)
                        if (!dir.exists())
                            dir.mkdirs()

                        val file = File(dir, "newFile.pdf")
                        val fOut = FileOutputStream(file)

                        PdfWriter.getInstance(doc, fOut)

                        //open the document
                        doc.open()
                        doc.add(getTitlePara("Device:"))
                        doc.add(dataPara("Device"))

                        doc.newPage()
                        doc.add(getTitlePara("Phone:"))
                        doc.add(dataPara("Phone"))

                        runOnUiThread {

                            Toast.makeText(this, "Created ${file.absolutePath}", Toast.LENGTH_SHORT)
                                    .show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this, "OOPS: ${e.localizedMessage}", Toast.LENGTH_SHORT)
                                    .show()
                        }

                    } finally {
                        doc.close()
                    }
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun getTitlePara(title: String): Paragraph {
        val p1 = Paragraph(title)
        val paraFont = Font(Font.FontFamily.TIMES_ROMAN)
        paraFont.setStyle(Font.BOLD)
        info { "Size: ${paraFont.size}" }
        paraFont.size = paraFont.size + 10f
        p1.alignment = Paragraph.ALIGN_LEFT
        p1.font = paraFont
        return p1
    }

    fun dataPara(info: String): Paragraph {
        val stringBuilder = StringBuilder()

        if (info.equals("Device", true)) {
            val allSimInfo = App().db()?.getSimDao()?.getAllSimInfo()

            allSimInfo?.forEach {
                stringBuilder.append("\n")
                stringBuilder.append(it.title + " : " + it.info)
            }
        } else {
            val allSimInfo = App().db()?.getPhoneDao()?.getAllPhoneInfo()

            allSimInfo?.forEach {
                stringBuilder.append("\n")
                stringBuilder.append(it.title + " : " + it.info)
            }
        }


        val p1 = Paragraph(stringBuilder.toString())
        val paraFont = Font(Font.FontFamily.ZAPFDINGBATS)
        p1.alignment = Paragraph.ALIGN_LEFT
        p1.font = paraFont

        return p1
    }
}
