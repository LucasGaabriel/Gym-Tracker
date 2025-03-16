package com.lucascosta.gymtracker.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.FragmentExercisesBinding
import com.lucascosta.gymtracker.ui.adapter.ListExerciseAdapter
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener

class ExercisesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listVM: ListExercisesViewModel
    private val adapter: ListExerciseAdapter = ListExerciseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addNewExercise.setOnClickListener(this)

        binding.recyclerListExercises.layoutManager = LinearLayoutManager(context)
        binding.recyclerListExercises.adapter = adapter

        listVM = ViewModelProvider(this)[ListExercisesViewModel::class.java]

        val listener = object : OnExerciseListener {
            override fun onClick(e: ExerciseModel) {
                val action =
                    ExercisesFragmentDirections.actionNavigationExercisesToNavigationAddExercise(e)
                findNavController().navigate(action)
            }
        }

        adapter.setListener(listener)
        listVM.getAllExercises()
        setObserver()

        return root
    }

    fun setObserver() {
        listVM.getExerciseList().observe(viewLifecycleOwner, Observer {
            adapter.updateExerciseList(it)
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.add_new_exercise) {
            findNavController().navigate(R.id.action_navigation_exercises_to_navigation_add_exercise)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}