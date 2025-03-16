package com.lucascosta.gymtracker.data.dao

import androidx.room.*
import com.lucascosta.gymtracker.data.model.RoutineExerciseCrossRef
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.data.model.RoutineWithExercises

@Dao
interface RoutineDAO {

    @Insert
    fun insert(r: RoutineModel): Long

    @Update
    fun update(r: RoutineModel): Int

    @Delete
    fun delete(r: RoutineModel): Int

    @Query("SELECT * FROM Routine WHERE routine_id = :id")
    fun getById(id: Int): RoutineModel

    @Query("SELECT * FROM Routine")
    fun getAllRoutines(): List<RoutineModel>

    @Transaction
    @Query("SELECT * FROM Routine")
    fun getRoutinesWithExercises(): List<RoutineWithExercises>

    @Transaction
    @Query("SELECT * FROM Routine WHERE routine_id = :routineId")
    fun getRoutineWithExercisesById(routineId: Int): RoutineWithExercises?

    @Insert
    fun insertRoutineExerciseCrossRef(crossRef: RoutineExerciseCrossRef): Long

    @Query("SELECT * FROM RoutineExerciseCrossRef WHERE routine_id = :routineId AND exercise_id = :exerciseId")
    fun getExerciseFromRoutine(routineId: Int, exerciseId: Int): RoutineExerciseCrossRef?
}