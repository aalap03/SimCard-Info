package com.example.aalap.simcardinfo.utils

import android.graphics.Color
import android.os.Environment
import com.example.aalap.simcardinfo.App
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.jetbrains.anko.AnkoLogger
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


class PdfUtils : AnkoLogger {

    fun createPDFFile(): File {
        val parentDir = File(Environment.getExternalStorageDirectory().absolutePath + "/SimCard-Info")
        if (!parentDir.exists())
            parentDir.mkdirs()

        val pdfFile = File(parentDir, "SimCard-Info_" + getCurrentTimeFormatted() + ".pdf")
        return pdfFile
    }

    private fun getCurrentTimeFormatted(): String {
        return SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    }

    fun writeIntoPDFFile(pdfFile: File) {
        val doc = Document()
        val fileOutputStream = FileOutputStream(pdfFile)
        PdfWriter.getInstance(doc, fileOutputStream)

        thread {

            doc.open()

            //first page
            doc.add(getTitlePara("Device:"))
            doc.add(getDataPara(true))


            doc.newPage()

            //second page
            doc.add(getTitlePara("Phone:"))
            doc.add(getDataPara(false))

            doc.close()
        }
    }

    private fun getTitlePara(title: String): Paragraph {
        val p1 = Paragraph(title)
        val paraFont = Font(Font.FontFamily.TIMES_ROMAN)
        paraFont.style = Font.BOLD
        paraFont.color = BaseColor.BLUE
        p1.alignment = Paragraph.ALIGN_CENTER
        p1.font = paraFont

        return p1
    }

    private fun getDataPara(isDeviceInfo: Boolean): Paragraph {
        val stringBuilder = StringBuilder()

        if (isDeviceInfo) {
            val allSimInfo = App().db()?.getSimDao()?.getAllSimInfo()

            allSimInfo?.forEach {
                stringBuilder.append("\n")
                stringBuilder.append(it.title + " : " + it.info)
                stringBuilder.append("\n")
            }
        } else {
            val isPhoneInfo = App().db()?.getPhoneDao()?.getAllPhoneInfo()

            isPhoneInfo?.forEach {
                stringBuilder.append("\n")
                stringBuilder.append(it.title + " : " + it.info)
                stringBuilder.append("\n")
            }
        }


        val p1 = Paragraph(stringBuilder.toString())
        val paraFont = Font(Font.FontFamily.ZAPFDINGBATS)
        p1.alignment = Paragraph.ALIGN_LEFT
        p1.font = paraFont

        return p1
    }

}