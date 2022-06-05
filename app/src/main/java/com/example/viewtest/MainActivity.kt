package com.example.viewtest

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

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

    val POSX = "posx"
    val POSY = "posy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view.x = 100f
        view.y = 100f

        updatePosition(savedInstanceState)
    }

    private fun updatePosition(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            view.x = 100f
            view.y = 100f
        } else {
            Handler().postDelayed({

                val displayWidth = displayMetrix.widthPixels
                val displayHeight = displayMetrix.heightPixels
                var xPos = 0
                var yPos = 0
                if (displayWidth > displayHeight) {
                    val parentPosition = IntArray(2)
                    parentLayout.getLocationOnScreen(parentPosition)

                    val prevY = savedInstanceState.getInt(POSY)
                    val prevX = savedInstanceState.getInt(POSX)
                    xPos = prevY - parentPosition[0]

                    var viewPositionFromScreenTop = displayMetrix.heightPixels - prevX
                    viewPositionFromScreenTop =
                        viewPositionFromScreenTop - parentPosition[1] - view.height
                    yPos = viewPositionFromScreenTop
                } else {
                    val parentPosition = IntArray(2)
                    parentLayout.getLocationOnScreen(parentPosition)

                    val prevY = savedInstanceState.getInt(POSY)
                    val prevX = savedInstanceState.getInt(POSX)
                    yPos = prevX - parentPosition[1]

                    var viewPositionFromScreenTop = displayMetrix.widthPixels - prevY
                    viewPositionFromScreenTop =
                        viewPositionFromScreenTop - parentPosition[0] - view.width
                    xPos = viewPositionFromScreenTop
                }
                view.x = xPos.toFloat()
                view.y = yPos.toFloat()
            }, 1000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val arr = IntArray(2)
        view.getLocationOnScreen(arr)

        outState.putInt(POSX, arr[0])
        outState.putInt(POSY, arr[1])

        Log.d("ashish", "onSaveInstanceState ${arr[0]} ${arr[1]}")

        super.onSaveInstanceState(outState)
    }

    private fun printChildPositionOnScreen() {
        Handler().postDelayed({
            val childPosition = IntArray(2)
            view.getLocationOnScreen(childPosition)

            Log.d("ashish", "printChildPositionOnScreen ${childPosition[0]} ${childPosition[1]}")
        }, 1000)
    }
}