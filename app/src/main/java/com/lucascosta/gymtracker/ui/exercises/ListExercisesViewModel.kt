package com.lucascosta.gymtracker.ui.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants

/**
 * ViewModel responsável por gerenciar a lista de exercícios no banco de dados.
 * Ele interage com a camada de persistência para obter todos os exercícios cadastrados
 * e os disponibiliza para a UI através de um LiveData.
 *
 * @param application A aplicação associada a este ViewModel.
 * @see AppDatabase Para a interação com o banco de dados.
 */
class ListExercisesViewModel(application: Application) : AndroidViewModel(application) {

    private var listMsg = MutableLiveData<Int>()
    private var exerciseList = MutableLiveData<List<ExerciseModel>>()

    fun getExerciseList(): LiveData<List<ExerciseModel>> {
        return exerciseList
    }

    fun getAllExercises() {
        val db = AppDatabase.getDatabase(getApplication()).ExerciseDAO()
        try {
            val resp = db.getAllExercises()
            listMsg.value = Constants.DB_MSGS.SUCCESS
            exerciseList.value = resp
        } catch (_: Exception) {
            listMsg.value = Constants.DB_MSGS.FAIL
        }
    }
}