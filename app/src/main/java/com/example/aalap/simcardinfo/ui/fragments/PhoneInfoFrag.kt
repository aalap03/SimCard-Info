package com.example.aalap.simcardinfo.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.database
import com.example.aalap.simcardinfo.db.Phone
import com.example.aalap.simcardinfo.db.PhoneDAO
import com.example.aalap.simcardinfo.ui.adapter.TitleValueAdapter
import kotlinx.android.synthetic.main.list_frag.*
import kotlin.concurrent.thread

class PhoneInfoFrag : Fragment() {

    lateinit var phoneDao: PhoneDAO
    lateinit var adapter: TitleValueAdapter
    var list = mutableListOf<Phone>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneDao = database.getPhoneDao()
        recycler_sim.layoutManager = LinearLayoutManager(context)

        thread {
            addInfo()
        }

    }

    @SuppressLint("MissingPermission")
    private fun addInfo() {

        phoneDao.addPhoneInfo(Phone(15, "Board", android.os.Build.BOARD))
        phoneDao.addPhoneInfo(Phone(16, "Bootloader", android.os.Build.BOOTLOADER))
        phoneDao.addPhoneInfo(Phone(17, "Brand", android.os.Build.BRAND))


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var abi = 0
            android.os.Build.SUPPORTED_ABIS.forEach {
                abi++
                phoneDao.addPhoneInfo(Phone(1, "Supported ABI-$abi", it))
            }
        }

        phoneDao.addPhoneInfo(Phone(1, "Device", android.os.Build.DEVICE))
        phoneDao.addPhoneInfo(Phone(2, "Display", android.os.Build.DISPLAY))
        phoneDao.addPhoneInfo(Phone(3, "Fingerprint", android.os.Build.FINGERPRINT))
        phoneDao.addPhoneInfo(Phone(4, "HARDWARE", android.os.Build.HARDWARE))
        phoneDao.addPhoneInfo(Phone(5, "HOST", android.os.Build.HOST))
        phoneDao.addPhoneInfo(Phone(6, "ID", android.os.Build.ID))
        phoneDao.addPhoneInfo(Phone(7, "MANUFACTURER", android.os.Build.MANUFACTURER))
        phoneDao.addPhoneInfo(Phone(8, "MODEL", android.os.Build.MODEL))
        phoneDao.addPhoneInfo(Phone(9, "PRODUCT", android.os.Build.PRODUCT))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            phoneDao.addPhoneInfo(Phone(10, "Serial", android.os.Build.getSerial()))
        }


        phoneDao.addPhoneInfo(Phone(11, "SECURITY_PATCH", android.os.Build.VERSION.SECURITY_PATCH))
        phoneDao.addPhoneInfo(Phone(12, "TYPE", android.os.Build.TYPE))
        phoneDao.addPhoneInfo(Phone(13, "User", android.os.Build.USER))
        phoneDao.addPhoneInfo(Phone(14, "Sdk_Int", android.os.Build.VERSION.SDK_INT.toString()))

        val size = phoneDao.getAllPhoneInfo().size
        list.addAll(phoneDao.getAllPhoneInfo())
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), "Phone Added $size", Toast.LENGTH_SHORT)
                    .show()
            adapter = TitleValueAdapter(requireContext(), list.toMutableList())
            recycler_sim.adapter = adapter
        }
    }
}