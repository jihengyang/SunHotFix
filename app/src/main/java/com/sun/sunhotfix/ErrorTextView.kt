package com.sun.sunhotfix

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * @author hengyangji
 * on 2021/11/8
 */
@SuppressLint("AppCompatCustomView")
class ErrorTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextView(context, attrs) {
    init {
        text = "right text view"
    }
}