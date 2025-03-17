package com.lucascosta.gymtracker.ui.exercises

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants

/**
 * ViewModel responsável por gerenciar as operações de adicionar, atualizar e excluir
 * exercícios na base de dados. Ele interage com a camada de persistência (Room) e
 * notifica a UI sobre o resultado das operações.
 *
 * @param application A aplicação em si, usada para acessar a base de dados.
 */
class AddExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private var savedMsg = MutableLiveData<Int>()

    fun getIsSaved(): LiveData<Int> {
        return savedMsg
    }

    fun saveExercise(e: ExerciseModel) {
        val db = AppDatabase.getDatabase(getApplication()).ExerciseDAO()
        var resp = 0L
        try {
            resp = db.insert(e)
            savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
        } catch (_: SQLiteConstraintException) {
            savedMsg.value = Constants.DB_MSGS.CONSTRAINT
        }
    }

    fun deleteExercise(e: ExerciseModel) {
        val db = AppDatabase.getDatabase(getApplication()).ExerciseDAO()
        try {
            val resp = db.delete(e)
            savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
        } catch (_: SQLiteConstraintException) {
            savedMsg.value = Constants.DB_MSGS.CONSTRAINT
        }
    }

    fun updateExercise(e: ExerciseModel) {
        val db = AppDatabase.getDatabase(getApplication()).ExerciseDAO()
        try {
            val resp = db.update(e)
            savedMsg.value = if (resp > 0) Constants.DB_MSGS.SUCCESS else Constants.DB_MSGS.FAIL
        } catch (_: SQLiteConstraintException) {
            savedMsg.value = Constants.DB_MSGS.CONSTRAINT
        }
    }
}