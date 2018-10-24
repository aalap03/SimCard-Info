package com.example.aalap.simcardinfo.utils

import com.itextpdf.text.Font
import com.itextpdf.text.FontFactory

class FontUtils {

    fun registerFont() {
        val pathFOnt = "/Users/aalap/Desktop/AndroidStudioProjects/SimCard-Info/app/src/main/res/font/lato_regular.ttf"
        FontFactory.register(pathFOnt)
    }

    companion object {
        fun getLatoReg(): Font? {
            return FontFactory.getFont("lato_regular.ttf")
        }
    }
}