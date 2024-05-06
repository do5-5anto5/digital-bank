package com.do55anto5.digitalbank.presenter.auth.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.FragmentRegisterBinding
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get()= _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() {

        binding.registerBtn.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        val confirmPassword = binding.editConfirmPassword.text.toString().trim()

        if (name.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (phone.isNotEmpty()) {
                    if (password.isNotEmpty()) {
                        if (confirmPassword.isNotEmpty() && confirmPassword == password) {

                            val user = User(name, email, phone, password)
                            registerUser(user)

                            Handler(Looper.getMainLooper()).postDelayed(this::popItBack, 3000)


                        } else {

                            Toast.makeText(
                                requireContext(), R.string.error_txt_confirmation,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    } else {

                        Toast.makeText(
                            requireContext(), R.string.error_txt_password, Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {

                    Toast.makeText(
                        requireContext(), R.string.error_txt_phone,
                        Toast.LENGTH_SHORT)
                        .show()

                }
            } else {

                Toast.makeText(requireContext(), R.string.error_txt_email,
                    Toast.LENGTH_SHORT
                ).show()

            }

        } else {
            Toast.makeText(requireContext(), R.string.error_txt_name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun registerUser(user: User) {

        registerViewModel.register(user).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false

                    Toast.makeText(requireContext(),
                        R.string.register_fragment_bottom_sheet_register_success,
                        Toast.LENGTH_SHORT).show()
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false

                    Toast.makeText(requireContext(), stateView.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun popItBack() = findNavController().popBackStack()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}