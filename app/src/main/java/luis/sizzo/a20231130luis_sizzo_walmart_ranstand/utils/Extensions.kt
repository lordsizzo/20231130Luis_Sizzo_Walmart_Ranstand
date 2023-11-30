package luis.sizzo.a20231130luis_sizzo_walmart_ranstand.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.R
import luis.sizzo.a20231130luis_sizzo_walmart_ranstand.view.adapters.CountriesAdapter


fun RecyclerView.settingsGrid(adapter: CountriesAdapter){
    this.layoutManager = GridLayoutManager(this.context, 2)
    this.adapter = adapter
}

fun RecyclerView.settingsLinearVertical(adapter: CountriesAdapter){
    this.layoutManager = LinearLayoutManager(this.context)
    this.adapter = adapter
}

fun Context.toast(message: String, lenght: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, lenght).show()
}

fun View.snack(message: String, lenght: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(this, message, lenght).show()
}

fun View.snackCustom(message: String, lenght: Int = Snackbar.LENGTH_SHORT){
    val snackbar = Snackbar.make(this, message, lenght)
    snackbar.withColor(ContextCompat.getColor(this.context, R.color.pink))
    snackbar.show()
}

fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar{
    this.view.setBackgroundColor(colorInt)
    return this
}

fun View.click(listener: (View) -> Unit){
    this.setOnClickListener{
        listener(it)
    }
}