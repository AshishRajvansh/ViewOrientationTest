package com.example.viewtest

import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    var posX: Int = 100
    var posY: Int = 100
    fun updateValue(x: Int, y: Int) {
        posX = x
        posY = y
    }
}