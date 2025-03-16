package com.lucascosta.gymtracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel.userFirstName.observe(viewLifecycleOwner) { firstName ->
            binding.welcomeMessage.text =
                String.format(getString(R.string.welcome_message), firstName)
        }

        homeViewModel.motivationalPhrase.observe(viewLifecycleOwner) { phrase ->
            binding.motivationalPhrase.text = phrase
        }

        binding.newExercise.setOnClickListener(this)
        binding.personalInfo.setOnClickListener(this)
        binding.motivationalPhrase.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.new_exercise) {
            // Navegar para a tela de adicionar exercício
            findNavController().navigate(R.id.action_navigation_home_to_navigation_add_exercise)
        } else if (v.id == R.id.personal_info) {
            // Navegar para a tela de perfil
            findNavController().navigate(R.id.action_navigation_home_to_navigation_profile)
        } else if (v.id == R.id.motivational_phrase) {
            // Mostrar nova frase motivacional
            homeViewModel.loadMotivationalPhrase()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}