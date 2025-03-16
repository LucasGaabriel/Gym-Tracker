package com.lucascosta.gymtracker.ui.routines

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.model.RoutineExerciseCrossRef
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants

class AddRoutineViewModel(application: Application) : AndroidViewModel(application) {

    private var savedMsg = MutableLiveData<Int>()

    fun getIsSaved(): LiveData<Int> {
        return savedMsg
    }

    fun saveRoutine(r: RoutineModel) {
        val db = AppDatabase.getDatabase(getApplication()).RoutineDAO()
        var resp = 0L
        try {
            resp = db.insert(r)
            savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
        } catch (_: SQLiteConstraintException) {
            savedMsg.value = Constants.DB_MSGS.CONSTRAINT
        }

    }

    fun deleteRoutine(r: RoutineModel) {
        val db = AppDatabase.getDatabase(getApplication()).RoutineDAO()
        try {
            val resp = db.delete(r)
            savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
        } catch (_: SQLiteConstraintException) {
            savedMsg.value = Constants.DB_MSGS.CONSTRAINT
        }
    }

    fun updateRoutine(r: RoutineModel) {
        val db = AppDatabase.getDatabase(getApplication()).RoutineDAO()
        try {
            val resp = db.update(r)
            savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
        } catch (_: SQLiteConstraintException) {
            savedMsg.value = Constants.DB_MSGS.CONSTRAINT
        }
    }

    fun addExerciseToRoutine(r: RoutineModel, e: ExerciseModel) {
        val db = AppDatabase.getDatabase(getApplication())
        val routineExerciseDao = db.RoutineDAO()

        // Verificar se o exercício já está associado à rotina
        val existingCrossRef = routineExerciseDao.getExerciseFromRoutine(r.routineId, e.exerciseId)

        if (existingCrossRef == null) {
            try {
                // Inserir o exercício na tabela de junção
                val crossRef = RoutineExerciseCrossRef(r.routineId, e.exerciseId)
                val resp = routineExerciseDao.insertRoutineExerciseCrossRef(crossRef)
                savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
            } catch (_: SQLiteConstraintException) {
                savedMsg.value = Constants.DB_MSGS.CONSTRAINT
            }
        } else {
            // Se o exercício já estiver na rotina, podemos retornar uma mensagem de sucesso ou falha.
            savedMsg.value = Constants.DB_MSGS.EXERCISE_ALREADY_EXISTS
        }
    }

    fun removeExerciseToRoutine(r: RoutineModel, e: ExerciseModel) {
        val db = AppDatabase.getDatabase(getApplication())
        val routineExerciseDao = db.RoutineDAO()

        // Verificar se o exercício já está associado à rotina
        val existingCrossRef = routineExerciseDao.getExerciseFromRoutine(r.routineId, e.exerciseId)

        if (existingCrossRef != null) {
            try {
                // Inserir o exercício na tabela de junção
                val crossRef = RoutineExerciseCrossRef(r.routineId, e.exerciseId)
                val resp = routineExerciseDao.deleteRoutineExerciseCrossRef(crossRef)
                savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
            } catch (_: SQLiteConstraintException) {
                savedMsg.value = Constants.DB_MSGS.CONSTRAINT
            }
        }
    }
}