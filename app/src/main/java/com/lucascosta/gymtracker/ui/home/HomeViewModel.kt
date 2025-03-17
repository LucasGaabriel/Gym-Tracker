package com.lucascosta.gymtracker.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.lucascosta.gymtracker.data.repository.MotivationalPhrases

/**
 * ViewModel para a tela inicial (Home) da aplicação.
 * Responsável por gerenciar os dados relacionados ao usuário (nome, progresso da rotina) e frases motivacionais.
 * Carrega os dados do usuário autenticado, o progresso da rotina e gerencia as frases motivacionais.
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _motivationalPhrase = MutableLiveData<String>()
    val motivationalPhrase: LiveData<String> = _motivationalPhrase

    private val _userFirstName = MutableLiveData<String?>()
    val userFirstName: LiveData<String?> = _userFirstName

    private var _totalWorkouts = MutableLiveData<Int>()
    val totalWorkouts: LiveData<Int> = _totalWorkouts

    private var _totalSets = MutableLiveData<Int>()
    val totalSets: LiveData<Int> = _totalSets

    private var _totalReps = MutableLiveData<Int>()
    val totalReps: LiveData<Int> = _totalReps

    private val motivationalPhrases = MotivationalPhrases(application)

    init {
        loadUserData()
        loadMotivationalPhrase()
        loadRoutineProgress()
    }

    /**
     * Carrega o nome do usuário autenticado no Firebase e extrai o primeiro nome.
     */
    private fun loadUserData() {
        val auth = FirebaseAuth.getInstance()
        val firstName = auth.currentUser?.displayName?.split(" ")?.firstOrNull()
        _userFirstName.value = firstName
    }

    fun loadMotivationalPhrase() {
        _motivationalPhrase.value = motivationalPhrases.getMotivationalPhrase()
    }

    /**
     * Define o progresso da rotina (total de treinos, séries e repetições) e salva no SharedPreferences.
     *
     * @param totalWorkouts O número total de treinos realizados.
     * @param totalSets O número total de séries realizadas.
     * @param totalReps O número total de repetições realizadas.
     */
    fun setRoutineProgress(totalWorkouts: Int, totalSets: Int, totalReps: Int) {
        _totalWorkouts.value = totalWorkouts
        _totalSets.value = totalSets
        _totalReps.value = totalReps

        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("RoutinePrefs", Context.MODE_PRIVATE)

        sharedPreferences.edit().apply {
            putInt("total_workouts", totalWorkouts)
            putInt("total_sets", totalSets)
            putInt("total_reps", totalReps)
            apply()
        }
    }

    /**
     * Carrega o progresso da rotina do usuário a partir do SharedPreferences.
     */
    fun loadRoutineProgress() {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("RoutinePrefs", Context.MODE_PRIVATE)
        _totalWorkouts.value = sharedPreferences.getInt("total_workouts", 0)
        _totalSets.value = sharedPreferences.getInt("total_sets", 0)
        _totalReps.value = sharedPreferences.getInt("total_reps", 0)
    }
}