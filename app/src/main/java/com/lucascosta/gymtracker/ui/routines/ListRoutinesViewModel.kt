package com.lucascosta.gymtracker.ui.routines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants

/**
 * ViewModel responsável pela busca de todas as rotinas associadas a exercícios.
 * Utiliza LiveData para fornecer a lista de rotinas com exercícios para a UI e controlar o status da operação.
 *
 * @param application O contexto da aplicação, fornecido pelo Android.
 */
class ListRoutinesViewModel(application: Application) : AndroidViewModel(application) {

    private var listMsg = MutableLiveData<Int>()
    private var routineList = MutableLiveData<List<RoutineWithExercises>>()

    fun getRoutineList(): LiveData<List<RoutineWithExercises>> {
        return routineList
    }

    fun getAllRoutines() {
        val db = AppDatabase.getDatabase(getApplication()).RoutineDAO()
        try {
            val resp = db.getRoutinesWithExercises()
            listMsg.value = Constants.DB_MSGS.SUCCESS
            routineList.value = resp
        } catch (_: Exception) {
            listMsg.value = Constants.DB_MSGS.FAIL
        }
    }
}