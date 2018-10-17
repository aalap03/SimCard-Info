package com.example.aalap.simcardinfo.db

interface SimDAO{

    fun addSimInfo(sim: Sim)

    fun getAllSimInfo(): List<Sim>
}