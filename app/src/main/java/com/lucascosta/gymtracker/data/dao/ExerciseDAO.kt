package com.lucascosta.gymtracker.data.dao

import androidx.room.*
import com.lucascosta.gymtracker.data.model.ExerciseModel

/**
 * Interface DAO (Data Access Object) para operações no banco de dados relacionadas à entidade ExerciseModel.
 */
@Dao
interface ExerciseDAO {

    @Insert
    fun insert(e: ExerciseModel): Long

    @Update
    fun update(e: ExerciseModel): Int

    @Delete
    fun delete(e: ExerciseModel): Int

    /**
     * Busca um exercício pelo seu ID.
     *
     * @param id O ID do exercício.
     * @return O exercício correspondente ao ID informado.
     */
    @Query("SELECT * FROM Exercise WHERE exercise_id = :id")
    fun getById(id: Int): ExerciseModel

    /**
     * Busca um exercício pelo seu nome.
     *
     * @param name O nome do exercício.
     * @return O exercício correspondente ao nome informado.
     */
    @Query("SELECT * FROM Exercise WHERE name = :name")
    fun getByName(name: String): ExerciseModel

    /**
     * Retorna todos os exercícios cadastrados no banco de dados, ordenados por nome em ordem crescente.
     *
     * @return Uma lista contendo todos os exercícios cadastrados.
     */
    @Query("SELECT * FROM Exercise ORDER BY name ASC")
    fun getAllExercises(): List<ExerciseModel>
}