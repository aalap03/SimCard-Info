package com.example.aalap.simcardinfo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [SimInfo::class, PhoneInfo::class], version = 2)
abstract class SimDatabase : RoomDatabase() {

    abstract fun getSimDao(): SimDAO

    abstract fun getPhoneDao(): PhoneDAO

}