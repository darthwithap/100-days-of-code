package me.darthwithap.covidtracker

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

class SpannableDelta(context: Context, text: String, color: Int, start: Int): SpannableString(text) {
    init {
        setSpan(
            ForegroundColorSpan(
                Color.parseColor(
                    String.format("#%06x", ContextCompat.getColor(context, color))
                )
            ),
            start,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}