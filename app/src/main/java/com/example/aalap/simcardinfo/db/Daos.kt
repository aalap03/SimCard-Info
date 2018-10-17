package com.example.aalap.simcardinfo.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface SimDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSimInfo(sim: Sim)

    @Query("select * from sim_table")
    fun getAllSimInfo(): List<Sim>
}


@Dao
interface PhoneDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhoneInfo(phone: Phone)

    @Query("select * from phone_info_table")
    fun getAllPhoneInfo(): List<Phone>
}