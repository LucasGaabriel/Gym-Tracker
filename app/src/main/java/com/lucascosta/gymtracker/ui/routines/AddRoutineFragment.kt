package com.lucascosta.gymtracker.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.data.room.AppDatabase
import com.lucascosta.gymtracker.databinding.FragmentAddRoutineBinding
import com.lucascosta.gymtracker.ui.adapter.ListExerciseInRoutineAdapter
import com.lucascosta.gymtracker.ui.exercises.ListExercisesViewModel
import com.lucascosta.gymtracker.ui.home.HomeViewModel
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener
import com.lucascosta.gymtracker.utils.Constants

class AddRoutineFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var addRoutineVM: AddRoutineViewModel
    private lateinit var listVM: ListExerciseInRoutineViewModel
    private lateinit var listExercisesVM: ListExercisesViewModel
    private lateinit var homeVM: HomeViewModel
    private val args: AddRoutineFragmentArgs by navArgs()
    private val adapter: ListExerciseInRoutineAdapter = ListExerciseInRoutineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)

        listVM = ViewModelProvider(this)[ListExerciseInRoutineViewModel::class.java]
        addRoutineVM = ViewModelProvider(this)[AddRoutineViewModel::class.java]
        listExercisesVM = ViewModelProvider(this)[ListExercisesViewModel::class.java]
        homeVM = ViewModelProvider(this)[HomeViewModel::class.java]

        val routine = args.routine
        routine?.let { populateFields(it) } // Se for edição, preencher os campos

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.exerciseList.layoutManager = LinearLayoutManager(context)
        binding.exerciseList.adapter = adapter

        binding.saveRoutine.setOnClickListener(this)
        binding.deleteRoutine.setOnClickListener(this)
        binding.addExercise.setOnClickListener(this)
        binding.removeExercise.setOnClickListener(this)
        binding.markAsDone.setOnClickListener(this)

        if (routine != null) {
            listVM.routineWithExercises.observe(
                viewLifecycleOwner,
                Observer { routineWithExercises ->
                    routineWithExercises?.let {
                        adapter.updateExerciseList(it.exercises)
                    }
                })
            listVM.getExercisesByRoutine(routine.routine.routineId)
        }

        val listener = object : OnExerciseListener {
            override fun onClick(e: ExerciseModel) {
                val action =
                    AddRoutineFragmentDirections.actionAddRoutineFragmentToNavigationAddExercise(e)
                findNavController().navigate(action)
            }
        }

        adapter.setListener(listener)

        setSpinnerExercises()
        setObserver()

        return binding.root
    }

    override fun onClick(view: View) {
        val routine = args.routine

        when (view.id) {
            R.id.save_routine -> {
                try {
                    val r = RoutineModel().apply {
                        this.routineId = routine?.routine?.routineId ?: 0
                        this.name = binding.routineName.text.toString()
                        this.description = binding.description.text.toString()
                    }

                    if (routine != null) {
                        addRoutineVM.updateRoutine(r)
                    } else {
                        addRoutineVM.saveRoutine(r)
                    }

                    requireActivity().onBackPressedDispatcher.onBackPressed()

                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        requireContext(),
                        "Preencha todos os campos corretamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.delete_routine -> {
                routine?.let { addRoutineVM.deleteRoutine(it.routine) }
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            R.id.add_exercise -> {
                val selectedExerciseName = binding.spinnerExercises.selectedItem.toString()

                // Buscar o exercício no banco de dados usando o nome selecionado
                val exerciseDao = AppDatabase.getDatabase(requireContext()).ExerciseDAO()
                val selectedExercise = exerciseDao.getByName(selectedExerciseName)

                if (routine != null) {
                    // Chamar a função do ViewModel para adicionar o exercício à rotina
                    addRoutineVM.addExerciseToRoutine(routine.routine, selectedExercise)
                    listVM.getExercisesByRoutine(routine.routine.routineId)
                }
            }

            R.id.remove_exercise -> {
                val selectedExerciseName = binding.spinnerExercises.selectedItem.toString()

                // Buscar o exercício no banco de dados usando o nome selecionado
                val exerciseDao = AppDatabase.getDatabase(requireContext()).ExerciseDAO()
                val selectedExercise = exerciseDao.getByName(selectedExerciseName)

                if (routine != null) {
                    // Chamar a função do ViewModel para adicionar o exercício à rotina
                    addRoutineVM.removeExerciseToRoutine(routine.routine, selectedExercise)
                    listVM.getExercisesByRoutine(routine.routine.routineId)
                }
            }

            R.id.mark_as_done -> {
                var totalWorkouts = homeVM.totalWorkouts.value ?: 0
                var totalSets = homeVM.totalSets.value ?: 0
                var totalReps = homeVM.totalReps.value ?: 0

                totalWorkouts += 1

                routine?.exercises?.let {
                    totalSets += it.sumOf { it.sets }
                    totalReps += it.sumOf { it.reps }
                }

                homeVM.setRoutineProgress(totalWorkouts, totalSets, totalReps)
                homeVM.loadRoutineProgress()

                Toast.makeText(
                    requireContext(),
                    "Rotina marcada como concluída com sucesso.",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setObserver() {
        addRoutineVM.getIsSaved().observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.DB_MSGS.SUCCESS -> {
                    Toast.makeText(
                        requireContext(),
                        "Rotina atualizada com sucesso.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                Constants.DB_MSGS.FAIL -> {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao adicionar rotina.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun populateFields(routine: RoutineWithExercises) {
        binding.routineName.setText(routine.routine.name)
        binding.description.setText(routine.routine.description)
    }

    private fun setSpinnerExercises() {
        listExercisesVM.getAllExercises()
        val exercisesNames = listExercisesVM.getExerciseList().value.orEmpty().map { it.name }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            exercisesNames
        )
        binding.spinnerExercises.adapter = adapter
    }
}
