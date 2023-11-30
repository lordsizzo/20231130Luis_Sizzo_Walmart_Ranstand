package luis.sizzo.a20231130_luis_sizzo_walmart_caspex.utils

import android.content.Context
import android.graphics.Color
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.R

class Utilities {

    companion object{
        fun getRandomColor(context: Context): Int {
            val allColors = context.resources.getStringArray(R.array.colors)
            val rnds = (allColors.indices).random()
            return Color.parseColor(allColors[rnds])
        }
    }
}