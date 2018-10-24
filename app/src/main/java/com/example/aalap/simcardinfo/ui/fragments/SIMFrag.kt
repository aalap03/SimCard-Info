package com.example.aalap.simcardinfo.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aalap.simcardinfo.utils.Permission
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.database
import com.example.aalap.simcardinfo.db.MyViewModel
import com.example.aalap.simcardinfo.db.SimInfo
import com.example.aalap.simcardinfo.ui.adapter.TitleValueAdapter
import com.example.aalap.simcardinfo.utils.Utils
import kotlinx.android.synthetic.main.list_frag.*
import org.jetbrains.anko.AnkoLogger
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.util.*
import kotlin.concurrent.thread

class SIMFrag : Fragment(), AnkoLogger {

    lateinit var telephonyManager: TelephonyManager
    lateinit var adapter: TitleValueAdapter
    var list = mutableListOf<SimInfo>()
    lateinit var viewModel: MyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        recycler_sim.layoutManager = LinearLayoutManager(context)

        viewModel = ViewModelProviders.of(this)
                .get(MyViewModel::class.java)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), Permission.READ_PHONE_STATE.code)
        else
            getSimInfo()

        test_button.setOnClickListener {
            var bitmap = Utils().getRecyclerViewScreenshot(recycler_sim)
            test_image.setImageBitmap(bitmap)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty()) {

            if (requestCode == Permission.READ_PHONE_STATE.code && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //yeyy
                getSimInfo()
            } else if (shouldShowRequestPermissionRationale(permissions[0])) {
                requestPermissions(permissions, requestCode)
            } else {
                //neyy

                Toast.makeText(requireContext(), "Fuck you bro", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getSimInfo() {

        val simDao = database.getSimDao()

        thread {
            simDao.addSimInfo(SimInfo(1, "Network Operator", telephonyManager.networkOperator))
            simDao.addSimInfo(SimInfo(2, "Device Software Version", telephonyManager.deviceSoftwareVersion))
            simDao.addSimInfo(SimInfo(3, "dataActivity", telephonyManager.dataActivity.toString()))
            simDao.addSimInfo(SimInfo(4, "voiceMailNumber", telephonyManager.voiceMailNumber))
            simDao.addSimInfo(SimInfo(5, "SimInfo State", telephonyManager.simState.toString()))
            simDao.addSimInfo(SimInfo(6, "SimInfo Country Iso", telephonyManager.simCountryIso))
            simDao.addSimInfo(SimInfo(7, "SimInfo Operator", telephonyManager.simOperator))
            simDao.addSimInfo(SimInfo(8, "SimInfo OperatorName", telephonyManager.simOperatorName))

            simDao.addSimInfo(SimInfo(9, "Sim1 Number", telephonyManager.line1Number))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                simDao.addSimInfo(SimInfo(10, "IMEI", telephonyManager.imei))
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                simDao.addSimInfo(SimInfo(11, "SimInfo Carrier Id", telephonyManager.simCarrierId.toString()))
                simDao.addSimInfo(SimInfo(12, "SimInfo CarrierId Name", telephonyManager.simCarrierIdName.toString()))
            }


            val size = simDao.getAllSimInfo().size
            list.clear()
            list.addAll(simDao.getAllSimInfo())
            viewModel.simInfoList = simDao.getAllSimInfo()

            activity?.runOnUiThread {
                Toast.makeText(requireContext(), "SIM Added $size", Toast.LENGTH_SHORT)
                        .show()
                adapter = TitleValueAdapter(requireContext(), list.toMutableList())
                recycler_sim.adapter = adapter
            }

        }
    }

}