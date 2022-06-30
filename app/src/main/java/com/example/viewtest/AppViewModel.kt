import androidx.lifecycle.ViewModel
import com.example.viewtest.CordCal
import com.example.viewtest.CordCalForLandscape
import com.example.viewtest.CordCalForPortrait

class AppViewModel : ViewModel() {

    var posX: Int = 100
    var posY: Int = 100
    fun updateValue(x: Int, y: Int) {
        posX = x
        posY = y
    }

    var isInitiallyPortrait: Boolean? = null
    fun getCordCal(portrait: Boolean): CordCal {
        if (isInitiallyPortrait == null) {
            isInitiallyPortrait = portrait
        }

        if (isInitiallyPortrait!!) {
            return CordCalForPortrait
        } else {
            return CordCalForLandscape
        }
    }

    var viewCoordinates: Coordinate? = null
}

data class Coordinate(val posX: Float, val posY: Float, val isPortrait: Boolean)