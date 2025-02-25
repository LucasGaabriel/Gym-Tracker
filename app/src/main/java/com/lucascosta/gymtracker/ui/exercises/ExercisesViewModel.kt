package com.lucascosta.gymtracker.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExercisesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Exercises Fragment"
    }
    val text: LiveData<String> = _text
}