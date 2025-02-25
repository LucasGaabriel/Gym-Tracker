package com.lucascosta.gymtracker.ui.routines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lucascosta.gymtracker.databinding.FragmentRoutinesBinding

class RoutinesFragment : Fragment() {

    private var _binding: FragmentRoutinesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val routinesViewModel =
            ViewModelProvider(this)[RoutinesViewModel::class.java]

        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRoutines
        routinesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}