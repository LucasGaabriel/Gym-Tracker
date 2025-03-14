package com.lucascosta.gymtracker.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascosta.gymtracker.databinding.FragmentRoutinesBinding
import com.lucascosta.gymtracker.ui.adapter.ListRoutineAdapter
import com.lucascosta.gymtracker.ui.listener.OnRoutineListener
import com.lucascosta.gymtracker.utils.Constants
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.R

class RoutinesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRoutinesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listVM: ListRoutinesViewModel
    private val adapter: ListRoutineAdapter = ListRoutineAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val routinesViewModel =
            ViewModelProvider(this)[RoutinesViewModel::class.java]

        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textRoutines
//        routinesViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.addNewRoutine.setOnClickListener(this)

        binding.recyclerListRoutines.layoutManager = LinearLayoutManager(context)
        binding.recyclerListRoutines.adapter = adapter

        listVM = ViewModelProvider(this)[ListRoutinesViewModel::class.java]

        val listener = object : OnRoutineListener {
            override fun onClick(r: RoutineWithExercises) {
                Toast.makeText(context, r.routine.name, Toast.LENGTH_SHORT).show()
            }
        }

        adapter.setListener(listener)
        listVM.getAllRoutines()
        setObserver()

        return root
    }

    fun setObserver() {
//        listVM.getListMsg().observe(viewLifecycleOwner, Observer {
//            if (it == Constants.DB_MSGS.SUCCESS) {
//                Toast.makeText(requireContext(), R.string.success_search_routines, Toast.LENGTH_SHORT).show()
//            } else if (it == Constants.DB_MSGS.FAIL) {
//                Toast.makeText(requireContext(), R.string.not_found_search_routines, Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })

        listVM.getRoutineList().observe(viewLifecycleOwner, Observer {
            adapter.updateRoutineList(it)
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.add_new_routine) {
            findNavController().navigate(R.id.action_navigation_routines_to_addRoutineFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}