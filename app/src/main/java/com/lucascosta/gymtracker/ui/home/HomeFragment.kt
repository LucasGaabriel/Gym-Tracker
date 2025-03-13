package com.lucascosta.gymtracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lucascosta.gymtracker.databinding.FragmentHomeBinding
import com.lucascosta.gymtracker.R

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.newExercise.setOnClickListener(this)
        binding.personalInfo.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.new_exercise) {
            // Navegar para a tela de adicionar exercício
            findNavController().navigate(R.id.action_navigation_home_to_navigation_add_exercise)
        } else if (v.id == R.id.personal_info) {
            // Navegar para a tela de perfil
            findNavController().navigate(R.id.action_navigation_home_to_navigation_profile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}