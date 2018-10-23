package com.example.aalap.simcardinfo.db

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.aalap.simcardinfo.App


open class MyViewModel(application: Application):  AndroidViewModel(application) {

    var simList: List<Sim> = mutableListOf()
    var simDAO: SimDAO? = null

    constructor() : this(App()) {
        simDAO = App().db()?.getSimDao()
    }

    fun getAllSim(): List<Sim>? {
        return simList
    }

}