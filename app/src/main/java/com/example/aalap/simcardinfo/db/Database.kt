package com.example.aalap.simcardinfo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Sim::class, Phone::class], version = 1)
abstract class Database: RoomDatabase() {

}