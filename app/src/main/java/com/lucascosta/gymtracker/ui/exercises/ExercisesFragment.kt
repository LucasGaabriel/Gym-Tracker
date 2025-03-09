package com.lucascosta.gymtracker.ui.exercises

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.FragmentExercisesBinding
import com.lucascosta.gymtracker.ui.adapter.ListExerciseAdapter
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener
import com.lucascosta.gymtracker.utils.Constants

class ExercisesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentExercisesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listVM: ListExercisesViewModel
    private val adapter: ListExerciseAdapter = ListExerciseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val exercisesViewModel =
            ViewModelProvider(this)[ExercisesViewModel::class.java]

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textExercises
//        exercisesViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.button.setOnClickListener(this)

        binding.recyclerListExercises.layoutManager = LinearLayoutManager(context)
        binding.recyclerListExercises.adapter = adapter

        listVM = ViewModelProvider(this)[ListExercisesViewModel::class.java]

        val listener = object : OnExerciseListener {
            override fun onClick(e: ExerciseModel) {
                Toast.makeText(context, e.name, Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setListener(listener)
        listVM.getAllExercises()
        setObserver()

        return root
    }

    fun setObserver() {
        listVM.getListMsg().observe(viewLifecycleOwner, Observer {
            if (it == Constants.DB_MSGS.SUCCESS) {
                Toast.makeText(requireContext(), R.string.success_search_exercises, Toast.LENGTH_SHORT).show()
            } else if (it == Constants.DB_MSGS.FAIL) {
                Toast.makeText(requireContext(), R.string.not_found_search_exercises, Toast.LENGTH_SHORT)
                    .show()
            }
        })

        listVM.getExerciseList().observe(viewLifecycleOwner, Observer {
            adapter.updateExerciseList(it)
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button) {
            startActivity(Intent(context, AddExerciseActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}