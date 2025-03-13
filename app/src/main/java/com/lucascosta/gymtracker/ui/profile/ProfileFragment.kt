package com.lucascosta.gymtracker.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.lucascosta.gymtracker.databinding.FragmentProfileBinding
import com.lucascosta.gymtracker.R
import com.lucascosta.gymtracker.ui.LoginActivity

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()
    private val profileViewModel: EditProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences =
            requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        binding.userName.text = FirebaseAuth.getInstance().currentUser?.displayName
        binding.birthdate.text = sharedPreferences.getString("birthdate", "")
        binding.height.text = sharedPreferences.getFloat("height", 0f).toString()
        binding.bodyWeight.text = sharedPreferences.getFloat("body_weight", 0f).toString()
        binding.bmi.text = "%.2f".format(sharedPreferences.getFloat("bmi", 0f))

        profileViewModel.birthdate.observe(viewLifecycleOwner) {
            binding.birthdate.text = it
        }
        profileViewModel.height.observe(viewLifecycleOwner) {
            if (it > 0) binding.height.text = it.toString()
        }
        profileViewModel.bodyWeight.observe(viewLifecycleOwner) {
            if (it > 0) binding.bodyWeight.text = it.toString()
        }
        profileViewModel.bmi.observe(viewLifecycleOwner) {
            binding.bmi.text = "%.2f".format(it)
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
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
