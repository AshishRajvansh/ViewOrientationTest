package com.example.viewtest

import AppViewModel
import Coordinate
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    val view: View by lazy {
        findViewById(R.id.view)
    }

    val parentLayout: LinearLayout by lazy {
        findViewById(R.id.parentLayout)
    }

    val displayMetrix: DisplayMetrics by lazy {
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
        appViewModel.getCordCal(isPortrait)
        if (savedInstanceState == null) {
            Log.d("ashish", "saveInstance state is null")
            view.x = appViewModel.posX.toFloat()
            view.y = appViewModel.posY.toFloat()

            Handler(Looper.getMainLooper())
                .postDelayed({
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
                }, 1000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
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
                    if (!currentInPortrait) {
                        // view.pivotX = 0f
                        // view.pivotY = 0f
                        // view.rotation = 270f
                        // val newY = view.y + view.height
                        // view.y = newY
                    } else {
                        // view.pivotX = 0f
                        // view.pivotY = 0f
                        //view.rotation = view.rotation+90
                    }
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
            }, 1000)
        }
    }

    private fun updatePosition2(savedInstanceState: Bundle?) {
        val isPortrait = isPortrait()
        if (savedInstanceState == null) {
            // Log.d("ashish", "saveInstance state is null")
            view.x = appViewModel.posX.toFloat()
            view.y = appViewModel.posY.toFloat()
            Handler(Looper.getMainLooper())
                .postDelayed({
                    val pivotPointCoordinates = appViewModel.getCordCal(isPortrait).getPivotPoint(
                        Dimensions(parentLayout.width, parentLayout.height),
                        Dimensions(view.width, view.height),
                        Point(view.x, view.y),
                        isPortrait()
                    )
                    appViewModel.viewCoordinates = Coordinate(
                        pivotPointCoordinates.x,
                        pivotPointCoordinates.y,
                        isPortrait()
                    )
                }, 1000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
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
                    // Handler(Looper.getMainLooper()).postDelayed({
                    //     if (!currentInPortrait) {
                    //         Log.d("ashish", "calX ${calX}   -  calY ${calY}")
                    //         Log.d("ashish", "width ${view.width}  height ${view.height}")
                    //         view.pivotX = 0f
                    //         view.pivotY = 0f
                    //         view.rotation = 270f
                    //         val newY = view.y + view.height
                    //         view.y = newY
                    //
                    //         // displayCoordinates = displayCoordinates.copy(y= newY)
                    //
                    //         // view.pivotX = 0f
                    //         // view.pivotY = view.height.toFloat()
                    //         // view.rotation = 90f//view.rotation - 90
                    //         // view.y -= view.width
                    //
                    //
                    //         // view.x -= view.width
                    //     } else {
                    //         //view.rotation = view.rotation+90
                    //     }
                    // }, 0)
                    val pivotPointCoordinates = appViewModel.getCordCal(isPortrait).getPivotPoint(
                        parentDimensions,
                        viewDimensions,
                        displayCoordinates,
                        currentInPortrait
                    )
                    appViewModel.viewCoordinates = Coordinate(
                        pivotPointCoordinates.x,
                        pivotPointCoordinates.y,
                        isPortrait()
                    )
                }
            }, 100)
        }
    }
}