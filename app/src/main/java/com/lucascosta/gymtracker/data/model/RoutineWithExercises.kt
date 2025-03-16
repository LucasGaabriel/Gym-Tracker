package com.lucascosta.gymtracker.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.io.Serializable

data class RoutineWithExercises(
    @Embedded val routine: RoutineModel,

    @Relation(
        parentColumn = "routine_id",
        entityColumn = "exercise_id",
        associateBy = Junction(RoutineExerciseCrossRef::class)
    )
    val exercises: List<ExerciseModel>
) : Serializable