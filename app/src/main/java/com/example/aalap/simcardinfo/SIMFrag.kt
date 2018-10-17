package com.example.aalap.simcardinfo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class SIMFrag: Fragment() {

    lateinit var telephonyManager: TelephonyManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sim_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
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
        telephonyManager.networkOperator
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