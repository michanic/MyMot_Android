package ru.michanic.mymot.Kotlin.Extensions

import android.graphics.Typeface
import ru.michanic.mymot.Kotlin.MyMotApplication

object Font {
    val progress =
        Typeface.createFromAsset(MyMotApplication.appContext?.assets, "fonts/Progress.ttf")
    val suzuki = Typeface.createFromAsset(MyMotApplication.appContext?.assets, "fonts/Suzuki.ttf")
    val oswald = Typeface.createFromAsset(MyMotApplication.appContext?.assets, "fonts/Oswald.ttf")
}