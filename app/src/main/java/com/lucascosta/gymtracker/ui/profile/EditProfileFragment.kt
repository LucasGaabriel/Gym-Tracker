package com.lucascosta.gymtracker.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.lucascosta.gymtracker.databinding.FragmentEditProfileBinding
import com.lucascosta.gymtracker.R

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by activityViewModels()
    private val args: EditProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        binding.username.text = FirebaseAuth.getInstance().currentUser?.displayName
        binding.birthdate.setText(args.birthdate)
        binding.height.setText(String.format(args.height.toString()))
        binding.bodyWeight.setText(String.format(args.bodyWeight.toString()))

        binding.saveProfile.setOnClickListener {
            saveProfile()
        }

        return binding.root
    }

    private fun saveProfile() {
        val birthdate = binding.birthdate.text.toString()
        val height = binding.height.text.toString().toFloatOrNull() ?: 0f
        val bodyWeight = binding.bodyWeight.text.toString().toFloatOrNull() ?: 0f

        if (height > 0 && bodyWeight > 0) {
            viewModel.setUserProfile(birthdate, height, bodyWeight)
            Toast.makeText(requireContext(), R.string.updated_profile_success, Toast.LENGTH_SHORT)
                .show()
            findNavController().popBackStack()
        } else {
            Toast.makeText(
                requireContext(), R.string.height_and_weight_greater_than_zero, Toast.LENGTH_SHORT
            ).show()
        }
    }
}
