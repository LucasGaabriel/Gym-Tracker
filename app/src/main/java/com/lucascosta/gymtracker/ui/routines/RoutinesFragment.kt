package com.lucascosta.gymtracker.ui.routines

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
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.databinding.FragmentRoutinesBinding
import com.lucascosta.gymtracker.ui.adapter.ListRoutineAdapter
import com.lucascosta.gymtracker.ui.listener.OnRoutineListener

/**
 * Fragment que exibe a lista de rotinas cadastradas, juntamente com seus exercícios associados.
 * Permite ao usuário visualizar, adicionar e editar rotinas.
 */
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
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addNewRoutine.setOnClickListener(this)

        binding.recyclerListRoutines.layoutManager = LinearLayoutManager(context)
        binding.recyclerListRoutines.adapter = adapter

        listVM = ViewModelProvider(this)[ListRoutinesViewModel::class.java]

        val listener = object : OnRoutineListener {
            override fun onClick(r: RoutineWithExercises) {
                val action =
                    RoutinesFragmentDirections.actionNavigationRoutinesToAddRoutineFragment(r)
                findNavController().navigate(action)
            }
        }

        adapter.setListener(listener)
        listVM.getAllRoutines()
        setObserver()

        return root
    }

    fun setObserver() {
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