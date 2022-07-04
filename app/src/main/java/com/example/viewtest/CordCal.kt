package com.example.viewtest

import android.util.Log
import android.view.View

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

    fun updateRotation(currentInPortrait: Boolean, view: View)
}

object CordCalForPortrait : CordCal {

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
        return point
    }

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
            Point(x, y)
        }
        return point
    }

    override fun updateRotation(currentInPortrait: Boolean, view: View) {
        if (!currentInPortrait) {
            view.pivotX = 0f
            view.pivotY = 0f
            view.rotation = 270f
            val newY = view.y + view.height
            view.y = newY
        }
    }
}

object CordCalForLandscape : CordCal {

    override fun getDisplayCoordinates(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        pivotPoint: Point,
        isPortrait: Boolean
    ): Point {
        val point = if (isPortrait) {
            val x = parentDimensions.width - pivotPoint.x - viewDimensions.width
            val y = pivotPoint.y
            Point(x, y)
        } else {
            Point(pivotPoint.y, pivotPoint.x)
        }
        return point
    }

    override fun getPivotPoint(
        parentDimensions: Dimensions,
        viewDimensions: Dimensions,
        viewCoordinates: Point,
        isPortrait: Boolean
    ): Point {
        val point = if (isPortrait) {
            val x = parentDimensions.width - viewDimensions.width - viewCoordinates.x
            val y = viewCoordinates.y
            Point(x, y)
        } else {
            Point(viewCoordinates.y, viewCoordinates.x)
        }
        return point
    }

    override fun updateRotation(currentInPortrait: Boolean, view: View) {
        if (currentInPortrait) {
            view.pivotX = 0f
            view.pivotY = 0f
            view.rotation = 90f
            val newX = view.x + view.width
            view.x = newX
        }
    }
}

data class Dimensions(val width: Int, val height: Int)
data class Point(val x: Float, val y: Float)
