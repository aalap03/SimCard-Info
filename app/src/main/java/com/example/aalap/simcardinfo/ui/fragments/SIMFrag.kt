package com.example.aalap.simcardinfo.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aalap.simcardinfo.App
import com.example.aalap.simcardinfo.utils.Permission
import com.example.aalap.simcardinfo.R
import com.example.aalap.simcardinfo.database
import com.example.aalap.simcardinfo.db.Sim
import kotlin.concurrent.thread

class SIMFrag: Fragment() {

    lateinit var telephonyManager: TelephonyManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sim_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


//        Room.databaseBuilder(requireContext().applicationContext, com.example.aalap.simcardinfo.db.SimDatabase::class.java, "SimDatabase")

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), Permission.READ_PHONE_STATE.code)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.isNotEmpty()) {

            if(requestCode == Permission.READ_PHONE_STATE.code && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //yeyy
                getSimInfo()
            }else if(shouldShowRequestPermissionRationale(permissions[0])){
                requestPermissions(permissions, requestCode)
            } else{
                //neyy

                Toast.makeText(requireContext(), "Fuck you bro", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getSimInfo() {

        val simDao = database?.getSimDao()

        thread {
            simDao?.addSimInfo(Sim(1, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(2, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(3, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(4, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(5, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(6, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(7, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(8, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(9, "Network Operator",  telephonyManager.networkOperator))
            simDao?.addSimInfo(Sim(10, "Network Operator",  telephonyManager.networkOperator))
        }






        telephonyManager.networkOperator
        telephonyManager.deviceSoftwareVersion
        telephonyManager.dataActivity
        telephonyManager.voiceMailNumber

        telephonyManager.simState

        telephonyManager.simCountryIso
        telephonyManager.simOperator
        telephonyManager.simOperatorName

        telephonyManager.line1Number


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.imei
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            telephonyManager.simCarrierId
            telephonyManager.simCarrierIdName
        }
    }
}