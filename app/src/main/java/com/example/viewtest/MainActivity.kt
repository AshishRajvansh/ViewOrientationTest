package com.example.viewtest

import AppViewModel
import Coordinate
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val view: View by lazy {
        findViewById(R.id.view)
    }

    private val parentLayout: LinearLayout by lazy {
        findViewById(R.id.parentLayout)
    }

    private val displayMetrix: DisplayMetrics by lazy {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics
    }

    lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        view.x = 0F
        view.y = 0F
        updatePosition(savedInstanceState)
    }

    private fun isPortrait(): Boolean {
        return displayMetrix.widthPixels < displayMetrix.heightPixels
    }

    private fun updatePosition(savedInstanceState: Bundle?) {
        val isPortrait = isPortrait()
        if (savedInstanceState == null) {
            view.x = appViewModel.initialPosX.toFloat()
            view.y = appViewModel.initialPosY.toFloat()

            parentLayout.post {
                val pivotPointCoordinates = appViewModel.getCordCal(isPortrait).getPivotPoint(
                    parentDimensions = Dimensions(parentLayout.width, parentLayout.height),
                    viewDimensions = Dimensions(view.width, view.height),
                    viewCoordinates = Point(view.x, view.y),
                    isPortrait()
                )
                appViewModel.viewCoordinates = Coordinate(
                    pivotPointCoordinates.x,
                    pivotPointCoordinates.y,
                    isPortrait()
                )
            }
        } else {
            parentLayout.post {
                val parentHeight = parentLayout.height
                val parentWidth = parentLayout.width
                appViewModel.viewCoordinates?.let { prevCord ->
                    val calX = prevCord.posX
                    val calY = prevCord.posY
                    val currentInPortrait = isPortrait()
                    if (prevCord.isPortrait == currentInPortrait) return@let
                    val parentDimensions = Dimensions(parentWidth, parentHeight)
                    val viewDimensions = Dimensions(view.width, view.height)
                    val displayCoordinates =
                        appViewModel.getCordCal(isPortrait).getDisplayCoordinates(
                            parentDimensions,
                            viewDimensions,
                            Point(calX, calY),
                            currentInPortrait
                        )
                    view.x = displayCoordinates.x
                    view.y = displayCoordinates.y

                    appViewModel.getCordCal(isPortrait).updateRotation(currentInPortrait, view)

                    val pivotPointCoordinates = appViewModel.getCordCal(isPortrait).getPivotPoint(
                        parentDimensions = parentDimensions,
                        viewDimensions = viewDimensions,
                        viewCoordinates = displayCoordinates,
                        currentInPortrait
                    )
                    appViewModel.viewCoordinates = Coordinate(
                        pivotPointCoordinates.x,
                        pivotPointCoordinates.y,
                        isPortrait()
                    )
                }
            }
        }
    }
}