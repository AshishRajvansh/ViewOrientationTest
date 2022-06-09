package com.example.viewtest

import android.os.Bundle
import android.os.Handler
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

    val POSX = "posx"
    val POSY = "posy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appViewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        Log.d("ashish", "isPortrait    ${isPortrait()} ------------------------------>")
        view.x = 0F
        view.y = 0F
        updatePosition(savedInstanceState)
    }

    private fun isPortrait(): Boolean {
        return displayMetrix.widthPixels < displayMetrix.heightPixels
    }

    private fun updatePosition(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            Log.d("ashish", "saveInstance state is null")
            view.x = appViewModel.posX.toFloat()
            view.y = appViewModel.posY.toFloat()
        } else {
            Handler().postDelayed({

                val displayWidth = displayMetrix.widthPixels
                val displayHeight = displayMetrix.heightPixels

                // val prevY = savedInstanceState.getInt(POSY)
                // val prevX = savedInstanceState.getInt(POSX)

                val prevY = appViewModel.posY
                val prevX = appViewModel.posX

                Log.d("ashish", "previous value ${prevX} ${prevY}")

                var xPos = 0
                var yPos = 0
                if (displayWidth > displayHeight) {
                    var landCord = appViewModel.landCord
                    if (landCord == null) {
                        val parentPosition = IntArray(2)
                        parentLayout.getLocationOnScreen(parentPosition)

                        xPos = prevY - parentPosition[0]

                        var viewPositionFromScreenTop = displayMetrix.heightPixels - prevX
                        viewPositionFromScreenTop =
                            viewPositionFromScreenTop - parentPosition[1] - view.height
                        yPos = viewPositionFromScreenTop
                        appViewModel.landCord = Coordinate(xPos, yPos)
                    } else {
                        xPos = landCord.posX
                        yPos = landCord.posY
                    }
                } else {
                    var porCord = appViewModel.porCord
                    if (porCord == null) {

                        val parentPosition = IntArray(2)
                        parentLayout.getLocationOnScreen(parentPosition)

                        yPos = prevX - parentPosition[1]

                        var viewPositionFromScreenTop = displayMetrix.widthPixels - prevY
                        viewPositionFromScreenTop =
                            viewPositionFromScreenTop - parentPosition[0] - view.width
                        xPos = viewPositionFromScreenTop
                        appViewModel.porCord = Coordinate(xPos, yPos)
                    } else {
                        xPos = porCord.posX
                        yPos = porCord.posY
                    }
                }
                Log.d("ashish", "new updated value ${xPos} ${yPos}")
                view.x = xPos.toFloat()
                view.y = yPos.toFloat()

                // Handler().postDelayed({
                //     updateValues()
                // }, 3000)
            }, 1000)
        }
    }

    override fun onPause() {
        super.onPause()
        updateValues()
        Log.d("ashish1", "onPause called------------>")
    }

    private fun updateValues() {
        val arr = IntArray(2)
        view.getLocationOnScreen(arr)
        appViewModel.updateValue(arr[0], arr[1])

        // val parentArr = IntArray(2)
        // parentLayout.getLocationOnScreen(parentArr)
        // Log.d("ashish", "updateValues ${arr[0]} ${arr[1]},  ${view.x} ${view.y},  ${parentArr[0]} ${parentArr[1]}")

        Log.d("ashish", "updateValues ${arr[0]} ${arr[1]},  ${view.x} ${view.y}, ${isPortrait()}")
        // val viewWindowPosition = IntArray(2)
        // view.getLocationInWindow(viewWindowPosition)
        //
        // Log.d("ashish", "windowPosition ${viewWindowPosition[0]} ${viewWindowPosition[1]}")
    }

    /*   private fun printChildPositionOnScreen() {
           Handler().postDelayed({
               val childPosition = IntArray(2)
               view.getLocationOnScreen(childPosition)

               Log.d("ashish", "printChildPositionOnScreen ${childPosition[0]} ${childPosition[1]}")
           }, 1000)
       }*/
}