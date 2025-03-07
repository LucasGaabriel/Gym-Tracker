package com.lucascosta.gymtracker.ui.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.utils.Constants

class ListExercisesViewModel(application: Application) : AndroidViewModel(application) {

    private var listMsg = MutableLiveData<Int>()
    private var exerciseList = MutableLiveData<List<ExerciseModel>>()

    fun getListMsg(): LiveData<Int> {
        return listMsg
    }

    fun getExerciseList(): LiveData<List<ExerciseModel>> {
        return exerciseList
    }

    fun getAllExercises() {
        val db = AppDatabase.getDatabase(getApplication()).ExerciseDAO()
        try {
            val resp = db.getAllExercises()
            listMsg.value = Constants.DbMessages.SUCCESS
            exerciseList.value = resp
        } catch (e: Exception) {
            listMsg.value = Constants.DbMessages.FAIL
        }
    }
}