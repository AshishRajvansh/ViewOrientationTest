package com.example.viewtest

import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {


    var posX: Int = 100
    var posY: Int = 100
    fun updateValue(x: Int, y: Int) {
        posX = x
        posY = y
    }

    var porCord: Coordinate? = null
    var landCord: Coordinate? = null

    /* fun updateValue(x: Int, y: Int, isPort: Boolean) {
         if (isPort) {
             if (porCord == null) {
                 porCord = Coordinate(x, y)
             }
         } else {
             if (landCord == null) {
                 landCord = Coordinate(x, y)
             }
         }
     }

     fun getCoordinate(isPort: Boolean): Coordinate? {
         return if (isPort) porCord
         else landCord
     }*/
}

data class Coordinate(val posX: Int, val posY: Int)
