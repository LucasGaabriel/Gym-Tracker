package com.lucascosta.gymtracker.ui.routines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants
import kotlinx.coroutines.launch

class ListExerciseInRoutineViewModel(application: Application) : AndroidViewModel(application) {

    private val _listMsg = MutableLiveData<Int>()
    val listMsg: LiveData<Int> get() = _listMsg

    private val _routineWithExercises = MutableLiveData<RoutineWithExercises>()
    val routineWithExercises: LiveData<RoutineWithExercises> get() = _routineWithExercises

    fun getExercisesByRoutine(routineId: Int) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(getApplication()).RoutineDAO()
            try {
                val routineWithExercises = db.getRoutineWithExercisesById(routineId)
                if (routineWithExercises != null) {
                    _listMsg.value = Constants.DB_MSGS.SUCCESS
                    _routineWithExercises.value = routineWithExercises
                } else {
                    _listMsg.value = Constants.DB_MSGS.FAIL
                }
            } catch (e: Exception) {
                _listMsg.value = Constants.DB_MSGS.FAIL
            }
        }
    }
}