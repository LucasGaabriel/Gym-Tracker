package com.lucascosta.gymtracker.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.model.PrimaryMuscle
import com.lucascosta.gymtracker.databinding.FragmentAddExerciseBinding
import com.lucascosta.gymtracker.utils.Constants

class AddExerciseFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddExerciseBinding
    private lateinit var addExerciseVM: AddExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExerciseBinding.inflate(inflater, container, false)

        // Configure edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveExercise.setOnClickListener(this)

        addExerciseVM = ViewModelProvider(this)[AddExerciseViewModel::class.java]
        setObserver()

        return binding.root
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
                activity?.onBackPressed()  // Close the fragment

            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObserver() {
        addExerciseVM.getIsSaved().observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.DB_MSGS.SUCCESS -> {
                    Toast.makeText(requireContext(), "Exercício adicionado com sucesso", Toast.LENGTH_SHORT).show()
                }
                Constants.DB_MSGS.FAIL -> {
                    Toast.makeText(requireContext(), "Erro ao adicionar exercício", Toast.LENGTH_SHORT).show()
                }
                Constants.DB_MSGS.CONSTRAINT -> {
                    Toast.makeText(requireContext(), "Já existe um exercício com esse nome", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
