package com.lucascosta.gymtracker.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lucascosta.gymtracker.databinding.FragmentProfileBinding
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.ui.LoginActivity

/**
 * Fragmento responsável pela exibição e interação com o perfil do usuário.
 * Permite ao usuário visualizar e editar seus dados pessoais, como data de nascimento, altura, peso e IMC.
 * Também oferece a funcionalidade de logout.
 */
class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val profileViewModel: EditProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences =
            requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        binding.userName.text = FirebaseAuth.getInstance().currentUser?.displayName
        binding.birthdate.text = sharedPreferences.getString("birthdate", "")
        binding.height.text = String.format(sharedPreferences.getFloat("height", 0f).toString())
        binding.bodyWeight.text =
            String.format(sharedPreferences.getFloat("body_weight", 0f).toString())
        binding.bmi.text = String.format(
            R.string.formatting_float_number.toString(), sharedPreferences.getFloat("bmi", 0f)
        )

        profileViewModel.birthdate.observe(viewLifecycleOwner) {
            binding.birthdate.text = it
        }
        profileViewModel.height.observe(viewLifecycleOwner) {
            if (it > 0) binding.height.text = String.format(it.toString())
        }
        profileViewModel.bodyWeight.observe(viewLifecycleOwner) {
            if (it > 0) binding.bodyWeight.text = String.format(it.toString())
        }
        profileViewModel.bmi.observe(viewLifecycleOwner) {
            when (it) {
                in 0f..18.5f -> {
                    binding.bmi.text = String.format(getString(R.string.underweight_format), it)
                    binding.bmi.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_500))
                }

                in 18.5f..24.9f -> {
                    binding.bmi.text = String.format(getString(R.string.normal_weight_format), it)
                    binding.bmi.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_500))
                }

                in 25f..29.9f -> {
                    binding.bmi.text = String.format(getString(R.string.overweight_format), it)
                    binding.bmi.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_500))
                }

                in 30f..Float.POSITIVE_INFINITY -> {
                    binding.bmi.text = String.format(getString(R.string.obesity_format), it)
                    binding.bmi.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_500))
                }

                else -> {
                    binding.bmi.text = getString(R.string.invalid_bmi)
                    binding.bmi.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
        }

        binding.editProfile.setOnClickListener(this)
        binding.logout.setOnClickListener(this)

        return root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.edit_profile) {
            val action = ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment(
                binding.birthdate.text.toString(),
                binding.bodyWeight.text.toString().toFloatOrNull() ?: 0f,
                binding.height.text.toString().toFloatOrNull() ?: 0f
            )
            findNavController().navigate(action)
        } else if (v.id == R.id.logout) {
            auth.signOut()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
