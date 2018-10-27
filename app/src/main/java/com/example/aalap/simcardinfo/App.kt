package com.example.aalap.simcardinfo

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import com.example.aalap.simcardinfo.db.Preference
import com.example.aalap.simcardinfo.db.SimDatabase
import kotlin.concurrent.thread

lateinit var database: SimDatabase
lateinit var pref: Preference

class App : Application() {


    override fun onCreate() {

        database = Room.databaseBuilder(applicationContext, SimDatabase::class.java, "SimDataBase")
                .fallbackToDestructiveMigration()
                .build()
        pref = Preference(this)


        super.onCreate()
    }

    fun db(): SimDatabase? {
         return database
    }
}