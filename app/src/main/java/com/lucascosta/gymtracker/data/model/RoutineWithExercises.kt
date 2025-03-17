package com.lucascosta.gymtracker.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.io.Serializable

/**
 * Representa uma rotina de exercícios com a lista de exercícios associados.
 *
 * Esta classe é usada para carregar uma rotina junto com seus exercícios relacionados a partir do banco de dados,
 * utilizando uma junção entre as tabelas [RoutineModel] e [ExerciseModel], através da tabela de relacionamento
 * [RoutineExerciseCrossRef].
 */
data class RoutineWithExercises(
    @Embedded val routine: RoutineModel,

    @Relation(
        parentColumn = "routine_id",
        entityColumn = "exercise_id",
        associateBy = Junction(RoutineExerciseCrossRef::class)
    )
    val exercises: List<ExerciseModel>
) : Serializable