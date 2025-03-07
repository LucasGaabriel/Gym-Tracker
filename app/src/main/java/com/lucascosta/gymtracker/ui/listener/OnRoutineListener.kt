package com.lucascosta.gymtracker.ui.listener

import com.lucascosta.gymtracker.data.model.RoutineWithExercises

interface OnRoutineListener {
    fun onClick(p: RoutineWithExercises)
}