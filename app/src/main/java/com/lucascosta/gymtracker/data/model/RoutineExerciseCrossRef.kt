package com.lucascosta.gymtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Classe de referência cruzada entre rotina e exercício, usada para representar o relacionamento
 * muitos-para-muitos entre [RoutineModel] e [ExerciseModel].
 *
 * Esta classe é uma entidade do Room Database, onde a chave primária é composta por [routineId] e [exerciseId].
 */
@Entity(primaryKeys = ["routine_id", "exercise_id"])
data class RoutineExerciseCrossRef(
    @ColumnInfo(name = "routine_id")
    var routineId: Int,

    @ColumnInfo(name = "exercise_id")
    var exerciseId: Int
)
