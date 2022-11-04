package com.example.shoplyte.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MSPTextViewBold (context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    init {
        //call the function to apply the fonts to the components

        applyFont()

    }

    private fun applyFont() {
        //This is used to get files from the assets folder and set it to the title TextView
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Raleway-Bold.ttf")
        setTypeface(typeface)
    }
}