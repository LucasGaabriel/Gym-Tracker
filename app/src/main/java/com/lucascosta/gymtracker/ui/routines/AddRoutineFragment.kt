package com.lucascosta.gymtracker.ui.routines

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.databinding.FragmentAddRoutineBinding
import com.lucascosta.gymtracker.ui.adapter.ListExerciseInRoutineAdapter
import com.lucascosta.gymtracker.ui.adapter.ListRoutineAdapter
import com.lucascosta.gymtracker.utils.Constants

class AddRoutineFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var addRoutineVM: AddRoutineViewModel
    private lateinit var listVM: ListExerciseInRoutineViewModel
    private val args: AddRoutineFragmentArgs by navArgs()
    private val adapter: ListExerciseInRoutineAdapter = ListExerciseInRoutineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)

        listVM = ViewModelProvider(this)[ListExerciseInRoutineViewModel::class.java]
        addRoutineVM = ViewModelProvider(this)[AddRoutineViewModel::class.java]

        val routine = args.routine
        routine?.let { populateFields(it) } // Se for edição, preencher os campos

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveRoutine.setOnClickListener(this)
        binding.deleteRoutine.setOnClickListener(this)

        if (routine != null) {
            listVM.routineWithExercises.observe(viewLifecycleOwner, Observer { routineWithExercises ->
                routineWithExercises?.let {
                    adapter.updateExerciseList(it.exercises)
                }
            })
            listVM.getExercisesByRoutine(routine.routine.routineId)
        }

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

                    Toast.makeText(
                        requireContext(),
                        if (routine != null) "Rotina atualizada com sucesso."
                        else "Rotina adicionada com sucesso.",
                        Toast.LENGTH_SHORT
                    ).show()

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
                try {
                    routine?.let { addRoutineVM.deleteRoutine(it.routine) }

                    Toast.makeText(requireContext(), "Rotina deletada com sucesso.", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Erro ao deletar rotina", Toast.LENGTH_SHORT).show()
                }
            }

//            R.id.add_exercise -> {
//                // Abrir um novo fragment para selecionar exercícios (Substitua pelo correto)
//                val action = AddRoutineFragmentDirections.actionAddRoutineFragmentToSelectExerciseFragment()
//                findNavController().navigate(action)
//            }
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

    private fun populateFields(routine: RoutineWithExercises) {
        binding.routineName.setText(routine.routine.name)
        binding.description.setText(routine.routine.description)
    }
}
