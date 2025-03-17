package com.lucascosta.gymtracker.data.dao

import androidx.room.*
import com.lucascosta.gymtracker.data.model.RoutineExerciseCrossRef
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.data.model.RoutineWithExercises

/**
 * Interface DAO (Data Access Object) para operações no banco de dados relacionadas à entidade RoutineModel
 * e suas associações com exercícios.
 */
@Dao
interface RoutineDAO {

    @Insert
    fun insert(r: RoutineModel): Long

    @Update
    fun update(r: RoutineModel): Int

    @Delete
    fun delete(r: RoutineModel): Int

    /**
     * Busca uma rotina pelo seu ID.
     *
     * @param id O ID da rotina.
     * @return A rotina correspondente ao ID informado.
     */
    @Query("SELECT * FROM Routine WHERE routine_id = :id")
    fun getById(id: Int): RoutineModel

    /**
     * Retorna todas as rotinas cadastradas no banco de dados.
     *
     * @return Uma lista contendo todas as rotinas cadastradas.
     */
    @Query("SELECT * FROM Routine")
    fun getAllRoutines(): List<RoutineModel>

    /**
     * Retorna todas as rotinas juntamente com seus exercícios associados.
     *
     * @return Uma lista contendo todas as rotinas e seus exercícios.
     */
    @Transaction
    @Query("SELECT * FROM Routine")
    fun getRoutinesWithExercises(): List<RoutineWithExercises>

    /**
     * Busca uma rotina específica pelo ID, incluindo seus exercícios associados.
     *
     * @param routineId O ID da rotina.
     * @return A rotina com seus exercícios, ou null se não for encontrada.
     */
    @Transaction
    @Query("SELECT * FROM Routine WHERE routine_id = :routineId")
    fun getRoutineWithExercisesById(routineId: Int): RoutineWithExercises?

    /**
     * Insere uma relação entre uma rotina e um exercício na tabela de relacionamento.
     *
     * @param crossRef A relação entre a rotina e o exercício.
     * @return O ID da relação inserida.
     */
    @Insert
    fun insertRoutineExerciseCrossRef(crossRef: RoutineExerciseCrossRef): Long

    /**
     * Remove uma relação entre uma rotina e um exercício do banco de dados.
     *
     * @param crossRef A relação a ser removida.
     * @return O número de linhas afetadas pela exclusão.
     */
    @Delete
    fun deleteRoutineExerciseCrossRef(crossRef: RoutineExerciseCrossRef): Int

    /**
     * Busca uma relação entre uma rotina e um exercício específico.
     *
     * @param routineId O ID da rotina.
     * @param exerciseId O ID do exercício.
     * @return A relação encontrada, ou null se não existir.
     */
    @Query("SELECT * FROM RoutineExerciseCrossRef WHERE routine_id = :routineId AND exercise_id = :exerciseId")
    fun getExerciseFromRoutine(routineId: Int, exerciseId: Int): RoutineExerciseCrossRef?
}