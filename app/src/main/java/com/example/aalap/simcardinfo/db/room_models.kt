package com.example.aalap.simcardinfo.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "sim_table")
data class SimInfo(@PrimaryKey(autoGenerate = true)
               var id: Int,
                   var title: String,
                   var info: String?)

@Entity(tableName = "phone_info_table")
data class PhoneInfo(@PrimaryKey(autoGenerate = true)
                 var id: Int,
                     var title: String,
                     var info: String?)