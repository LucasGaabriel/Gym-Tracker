package com.lucascosta.gymtracker.ui.exercises

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.model.PrimaryMuscle
import com.lucascosta.gymtracker.databinding.ActivityAddExerciseBinding
import com.lucascosta.gymtracker.utils.Constants

class AddExerciseActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityAddExerciseBinding
    private lateinit var addExerciseVM: AddExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveExercise.setOnClickListener(this)

        addExerciseVM = ViewModelProvider(this)[AddExerciseViewModel::class.java]
        setObserver()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.save_exercise) {

            try {
                val e = ExerciseModel().apply {
                    this.name = binding.exerciseName.text.toString()

                    // TODO: Modificar isso, pois quero usar nomes mais amigáveis no Spinner,
                    // TODO: mas mesmo assim, pegar o nome corretamente do enum
                    val selectedMuscle = binding.muscle.selectedItem.toString()
                    this.primaryMuscle = PrimaryMuscle.valueOf(selectedMuscle)

                    this.sets = binding.sets.text.toString().toInt()
                    this.reps = binding.reps.text.toString().toInt()
                    this.weight = binding.weight.text.toString().toFloat()
                    this.personalRecord = binding.personalRecord.text.toString().toFloat()
                    this.notes = binding.notes.text.toString()
                }

                addExerciseVM.saveExercise(e)
                finish()

            } catch (e: NumberFormatException){
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObserver(){
        addExerciseVM.getIsSaved().observe(this, Observer {
            if (it == Constants.DB_MSGS.SUCCESS) {
                Toast.makeText(this, "Exercício adicionado com sucesso", Toast.LENGTH_SHORT).show()
            } else if (it == Constants.DB_MSGS.FAIL) {
                Toast.makeText(this, "Erro ao adicionar exercício", Toast.LENGTH_SHORT).show()
            } else if (it == Constants.DB_MSGS.CONSTRAINT) {
                Toast.makeText(this, "Já existe um exercício com esse nome", Toast.LENGTH_SHORT).show()
            }
        })
    }
}