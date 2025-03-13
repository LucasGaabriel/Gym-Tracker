package com.lucascosta.gymtracker.ui.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

    private val _birthdate = MutableLiveData<String>()
    val birthdate: LiveData<String> get() = _birthdate

    private val _height = MutableLiveData<Float>()
    val height: LiveData<Float> get() = _height

    private val _bodyWeight = MutableLiveData<Float>()
    val bodyWeight: LiveData<Float> get() = _bodyWeight

    private val _bmi = MutableLiveData<Float>()
    val bmi: LiveData<Float> get() = _bmi

    init {
        _birthdate.value = sharedPreferences.getString("birthdate", "") ?: ""
        _height.value = sharedPreferences.getFloat("height", 0f)
        _bodyWeight.value = sharedPreferences.getFloat("body_weight", 0f)
        _bmi.value = sharedPreferences.getFloat("bmi", 0f)
    }

    fun setUserProfile(birthdate: String, height: Float, bodyWeight: Float) {
        val bmiValue = calculateBMI(height, bodyWeight)

        _birthdate.value = birthdate
        _height.value = height
        _bodyWeight.value = bodyWeight
        _bmi.value = bmiValue

        val editor = sharedPreferences.edit()
        editor.putString("birthdate", birthdate)
        editor.putFloat("height", height)
        editor.putFloat("body_weight", bodyWeight)
        editor.putFloat("bmi", bmiValue)
        editor.apply()
    }

    private fun calculateBMI(height: Float, weight: Float): Float {
        return if (height > 0) weight / ((height / 100) * (height / 100)) else -1f
    }
}
