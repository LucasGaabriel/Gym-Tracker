package com.lucascosta.gymtracker.ui.routines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants

class ListRoutinesViewModel(application: Application) : AndroidViewModel(application) {

    private var listMsg = MutableLiveData<Int>()
    private var routineList = MutableLiveData<List<RoutineWithExercises>>()

    fun getListMsg(): LiveData<Int> {
        return listMsg
    }

    fun getRoutineList(): LiveData<List<RoutineWithExercises>> {
        return routineList
    }

    fun getAllRoutines() {
        val db = AppDatabase.getDatabase(getApplication()).RoutineDAO()
        try {
            val resp = db.getRoutinesWithExercises()
            listMsg.value = Constants.DB_MSGS.SUCCESS
            routineList.value = resp
        } catch (e: Exception) {
            listMsg.value = Constants.DB_MSGS.FAIL
        }
    }
}