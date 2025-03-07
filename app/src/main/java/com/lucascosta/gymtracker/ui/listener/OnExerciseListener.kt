package com.lucascosta.gymtracker.ui.listener

import com.lucascosta.gymtracker.data.model.ExerciseModel

interface OnExerciseListener {
    fun onClick(e: ExerciseModel)
}