package com.lucascosta.gymtracker.data.dao

import androidx.room.*
import com.lucascosta.gymtracker.data.model.ExerciseModel

@Dao
interface ExerciseDAO {

    @Insert
    fun insert(e: ExerciseModel): Long

    @Update
    fun update(e: ExerciseModel): Int

    @Delete
    fun delete(e: ExerciseModel)

    @Query("SELECT * FROM Exercise WHERE exercise_id = :id")
    fun getById(id: Int): ExerciseModel

    @Query("SELECT * FROM Exercise WHERE name = :name")
    fun getByName(name: String): ExerciseModel

    @Query("SELECT * FROM Exercise")
    fun getAllExercises(): List<ExerciseModel>
}