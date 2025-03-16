package com.lucascosta.gymtracker.data.repository

import android.content.Context
import com.lucascosta.gymtracker.R

class MotivationalPhrases(context: Context) {
    private val phrases: Array<String> = arrayOf(
        context.getString(R.string.phrase_1),
        context.getString(R.string.phrase_2),
        context.getString(R.string.phrase_3),
        context.getString(R.string.phrase_4),
        context.getString(R.string.phrase_5),
        context.getString(R.string.phrase_6),
        context.getString(R.string.phrase_7),
        context.getString(R.string.phrase_8),
        context.getString(R.string.phrase_9),
        context.getString(R.string.phrase_10),
        context.getString(R.string.phrase_11),
        context.getString(R.string.phrase_12),
        context.getString(R.string.phrase_13),
        context.getString(R.string.phrase_14),
        context.getString(R.string.phrase_15),
        context.getString(R.string.phrase_16),
        context.getString(R.string.phrase_17),
        context.getString(R.string.phrase_18),
        context.getString(R.string.phrase_19),
        context.getString(R.string.phrase_20),
        context.getString(R.string.phrase_21),
        context.getString(R.string.phrase_22),
        context.getString(R.string.phrase_23),
        context.getString(R.string.phrase_24),
        context.getString(R.string.phrase_25),
        context.getString(R.string.phrase_26),
        context.getString(R.string.phrase_27),
        context.getString(R.string.phrase_28),
        context.getString(R.string.phrase_29),
        context.getString(R.string.phrase_30)
    )

    fun getMotivationalPhrase(): String {
        return phrases.random()
    }

}