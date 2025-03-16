package com.lucascosta.gymtracker.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.lucascosta.gymtracker.data.repository.MotivationalPhrases

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _motivationalPhrase = MutableLiveData<String>()
    val motivationalPhrase: LiveData<String> = _motivationalPhrase

    private val _userFirstName = MutableLiveData<String?>()
    val userFirstName: LiveData<String?> = _userFirstName

    private val motivationalPhrases = MotivationalPhrases(application)

    init {
        loadUserData()
        loadMotivationalPhrase()
    }

    private fun loadUserData() {
        val auth = FirebaseAuth.getInstance()
        val firstName = auth.currentUser?.displayName?.split(" ")?.firstOrNull()
        _userFirstName.value = firstName
    }

    fun loadMotivationalPhrase() {
        _motivationalPhrase.value = motivationalPhrases.getMotivationalPhrase()
    }
}