import androidx.lifecycle.ViewModel
import com.example.viewtest.CordCal
import com.example.viewtest.CordCalForLandscape
import com.example.viewtest.CordCalForPortrait

class AppViewModel : ViewModel() {

    var initialPosX: Int = 100
    var initialPosY: Int = 100

    private var isInitiallyPortrait: Boolean? = null
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