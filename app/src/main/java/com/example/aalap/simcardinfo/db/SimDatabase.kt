package com.example.aalap.simcardinfo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Sim::class, Phone::class], version = 1)
abstract class SimDatabase : RoomDatabase() {

    abstract fun getSimDao(): SimDAO

    abstract fun getPhoneDao(): PhoneDAO

}