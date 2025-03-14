package com.lucascosta.gymtracker.ui.routines

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.navArgs
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.model.PrimaryMuscle
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.databinding.FragmentAddExerciseBinding
import com.lucascosta.gymtracker.databinding.FragmentAddRoutineBinding
import com.lucascosta.gymtracker.ui.exercises.AddExerciseFragmentArgs
import com.lucascosta.gymtracker.ui.exercises.AddExerciseViewModel
import com.lucascosta.gymtracker.utils.Constants
import java.io.Serializable

class AddRoutineFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var addRoutineVM: AddRoutineViewModel
    private val args: AddRoutineFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)


        val routine = args.routine
        routine?.let { populateFields(it) } // Se for edição, preencher os campos

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveRoutine.setOnClickListener(this)
        binding.deleteRoutine.setOnClickListener(this)

        addRoutineVM = ViewModelProvider(this)[AddRoutineViewModel::class.java]
        setObserver()

        return binding.root
    }

    override fun onClick(view: View) {
        if (view.id == R.id.save_routine) {
            try {
                var id: Int
                val routine = args.routine
                routine?.let { id = it.routineId }

//                val exercises = args.exercises

                val r = RoutineModel().apply {
                    this.routineId = routine?.routineId ?: 0 // Set the ID if it's an existing routine
                    this.name = binding.routineName.text.toString()
                }

                if (routine != null) {
                    addRoutineVM.updateRoutine(r) // Update if it's an existing routine
                } else {
                    addRoutineVM.saveRoutine(r) // Save if it's a new routine
                }

                Toast.makeText(
                    requireContext(),
                    if (routine != null)
                        "Rotina atualizada com sucesso."
                    else "Rotina adicionada com sucesso.",
                    Toast.LENGTH_SHORT
                ).show()

                requireActivity().onBackPressedDispatcher.onBackPressed() // Close the fragment

            } catch (e: NumberFormatException) {
                Toast.makeText(
                    requireContext(),
                    "Preencha todos os campos corretamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (view.id == R.id.delete_routine) {
            try {
                val routine = args.routine
                routine?.let { addRoutineVM.deleteRoutine(it) }

                Toast.makeText(
                    requireContext(),
                    "Rotina deletada com sucesso.",
                    Toast.LENGTH_SHORT
                ).show()

                requireActivity().onBackPressedDispatcher.onBackPressed() // Close the fragment
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Erro ao deletar rotina",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setObserver() {
        addRoutineVM.getIsSaved().observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.DB_MSGS.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        "Rotina adicionada com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Constants.DB_MSGS.FAIL -> {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao adicionar rotina",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Constants.DB_MSGS.CONSTRAINT -> {
                    Toast.makeText(
                        requireContext(),
                        "Já existe uma rotina com esse nome",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun populateFields(routine: RoutineModel) {
        binding.routineName.setText(routine.name)
        binding.description.setText(routine.description)
    }
}
