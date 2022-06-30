package com.example.viewtest

import android.util.Log

interface CordCal {
    /*
    * This function takes pivot point and return the display coordinates
    * isPortrait -> Is currently in portrait
    * */
    fun getDisplayCoordinates(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        pivotPoint: Point,
        isPortrait: Boolean
    ): Point

    /*
         * This function takes the display coordinates and return the pivot points.
         * isPortrait -> Is currently in portrait
         * */
    fun getPivotPoint(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        viewCoordinates: Point,
        isPortrait: Boolean
    ): Point
}

object CordCalForPortrait : CordCal {
    /*
* This function takes pivot point and return the display coordinates
* isPortrait -> Is currently in portrait
* */
    override fun getDisplayCoordinates(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        pivotPoint: Point,
        isPortrait: Boolean
    ): Point {
        val point = if (isPortrait) {
            pivotPoint
        } else {
            val x = pivotPoint.y
            val y = parentDimensions.height - pivotPoint.x - viewDimensions.height

            Point(x, y)
        }
        Log.d(
            "ashish",
            "getDisplayCoordinates() x:${point.x}  y:${point.y} isPortrait: $isPortrait"
        )
        return point
    }

    /*
     * This function takes the display coordinates and return the pivot points.
     * isPortrait -> Is currently in portrait
     * */
    override fun getPivotPoint(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        viewCoordinates: Point,
        isPortrait: Boolean
    ): Point {
        val point = if (isPortrait) {
            viewCoordinates
        } else {
            val x = parentDimensions.height - viewCoordinates.y - viewDimensions.height
            val y = viewCoordinates.x

            Log.d(
                "ashish",
                "getPivotPoint() in Landscape  ${parentDimensions.height} - ${viewCoordinates.y} - ${viewDimensions.height}"
            )
            Point(x, y)
        }
        Log.d("ashish", "getPivotPoint() x:${point.x}  y:${point.y} isPortrait: $isPortrait")
        return point
    }
}

object CordCalForLandscape : CordCal {

    /*
* This function takes pivot point and return the display coordinates
* isPortrait -> Is currently in portrait
* */
    override fun getDisplayCoordinates(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        pivotPoint: Point,
        isPortrait: Boolean
    ): Point {
        val point = if (isPortrait) {
            val x = parentDimensions.width - pivotPoint.y - viewDimensions.width
            // Log.d("ashish", "getPivotPoint portrait ${parentDimensions.width} - ${viewCoordinates.y} - ${viewDimensions.width}")
            Log.d("ashish", " running")
            val y = pivotPoint.x
            Point(x, y)
        } else {
            pivotPoint
            // val x = pivotPoint.y
            // val y = parentDimensions.height - pivotPoint.x - viewDimensions.height

            // Point(x, y)
        }
        Log.d(
            "ashish",
            "getDisplayCoordinatesLandscape() x:${point.x}  y:${point.y} isPortrait: $isPortrait"
        )
        return point
    }

    /*
     * This function takes the display coordinates and return the pivot points.
     * isPortrait -> Is currently in portrait
     * */
    override fun getPivotPoint(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        viewCoordinates: Point,
        isPortrait: Boolean
    ): Point {
        val point = if (isPortrait) {
            val x = parentDimensions.width - viewDimensions.width - viewCoordinates.x
            // Log.d("ashish", "getPivotPoint portrait ${parentDimensions.width} - ${viewCoordinates.y} - ${viewDimensions.width}")
            val y = viewCoordinates.y
            Point(x, y)
        } else {
            viewCoordinates
            // val x = parentDimensions.height - viewCoordinates.y - viewDimensions.height
            // val y = viewCoordinates.x
            //
            // Log.d(
            //     "ashish",
            //     "getPivotPoint() in Landscape  ${parentDimensions.height} - ${viewCoordinates.y} - ${viewDimensions.height}"
            // )
            // Point(x, y)
        }
        Log.d("ashish", "getPivotPointLandscape() x:${point.x}  y:${point.y} isPortrait: $isPortrait")
        return point
    }
}

data class Dimensions(val width: Int, val height: Int)
data class Point(val x: Float, val y: Float)
