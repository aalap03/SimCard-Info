package com.example.aalap.simcardinfo.db

import android.arch.lifecycle.ViewModel

class MyViewModel(var list: MutableList<Sim>): ViewModel() {

    var title = "Title"
    var value = "Value"

    fun getAllSimInfo(): MutableList<Sim> {
        return list
    }


}