package com.example.aalap.simcardinfo

import android.app.Application
import android.arch.persistence.room.Database
import com.example.aalap.simcardinfo.db.SimDatabase
import kotlin.concurrent.thread

var database: SimDatabase? = null

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        database = SimDatabase.database(this)
    }

    fun db(): SimDatabase? {
         return database
    }
}