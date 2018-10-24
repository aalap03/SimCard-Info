package com.example.aalap.simcardinfo.utils

import android.app.Activity
import com.google.common.io.Flushables.flush
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.text.style.ParagraphStyle
import android.util.Log
import android.view.PixelCopy
import android.view.View
import com.itextpdf.text.*
import kotlinx.android.synthetic.main.list_frag.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import com.itextpdf.text.pdf.PdfWriter
import java.io.IOException
import android.support.v7.widget.RecyclerView




class Utils {

    fun takeScreenshot(view: View): Bitmap? {

        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false

        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"

            // create bitmap screen capture


            val imageFile = File(mPath)

            val outputStream = FileOutputStream(imageFile)
            val quality = 100

            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()



        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }

        return bitmap

    }

    fun createandDisplayPdf(text: String) {

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

            val p1 = Paragraph(text)
            val paraFont = Font(Font.FontFamily.COURIER)
            //val latoRegFont= FontFactory.getFont("lato_regular.ttf")
            paraFont.size = 20f
            p1.alignment = Paragraph.ALIGN_CENTER
            p1.font = paraFont



            //add paragraph to document
            doc.add(p1)

        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } catch (e: IOException) {
            Log.e("PDFCreator", "ioException:$e")
        } finally {
            doc.close()
        }

//        viewPdf("newFile.pdf", "Dir")
    }

    fun getRecyclerViewScreenshot(view: RecyclerView): Bitmap {
        val size = view.adapter!!.itemCount
        val holder = view.adapter!!.createViewHolder(view, 0)
        view.adapter!!.onBindViewHolder(holder, 0)
        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        holder.itemView.layout(0, 0, holder.itemView.measuredWidth, holder.itemView.measuredHeight)
        val bigBitmap = Bitmap.createBitmap(view.measuredWidth, holder.itemView.measuredHeight * size,
                Bitmap.Config.ARGB_8888)
        val bigCanvas = Canvas(bigBitmap)
        bigCanvas.drawColor(Color.WHITE)
        val paint = Paint()
        var iHeight = 0f
        holder.itemView.isDrawingCacheEnabled = true
        holder.itemView.buildDrawingCache()
        bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight, paint)
        holder.itemView.isDrawingCacheEnabled = false
        holder.itemView.destroyDrawingCache()
        iHeight += holder.itemView.measuredHeight
        for (i in 1 until size) {
            view.adapter!!.onBindViewHolder(holder, i)
            holder.itemView.isDrawingCacheEnabled = true
            holder.itemView.buildDrawingCache()
            bigCanvas.drawBitmap(holder.itemView.drawingCache, 0f, iHeight, paint)
            iHeight += holder.itemView.measuredHeight
            holder.itemView.isDrawingCacheEnabled = false
            holder.itemView.destroyDrawingCache()
        }
        return bigBitmap
    }
}