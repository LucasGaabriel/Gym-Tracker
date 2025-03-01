package com.lucascosta.gymtracker.ui.exercises

import com.lucascosta.gymtracker.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lucascosta.gymtracker.databinding.FragmentExercisesBinding

class ExercisesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentExercisesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val exercisesViewModel =
            ViewModelProvider(this)[ExercisesViewModel::class.java]

        _binding = FragmentExercisesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textExercises
        exercisesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.button.setOnClickListener(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button) {
            startActivity(Intent(context, ExerciseDetailActivity::class.java))
        }
    }
}