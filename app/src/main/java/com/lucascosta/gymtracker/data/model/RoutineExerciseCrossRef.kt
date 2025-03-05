package com.lucascosta.gymtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["routine_id", "exercise_id"])
data class RoutineExerciseCrossRef(
    @ColumnInfo(name = "routine_id")
    var routineId: Int,

    @ColumnInfo(name = "exercise_id")
    var exerciseId: Int
)
