package com.do55anto5.digitalbank.presenter.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.FragmentRegisterBinding
import com.do55anto5.digitalbank.presenter.profile.ProfileViewModel
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

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
        val phone = binding.editPhone.unMaskedText
        val password = binding.editPassword.text.toString().trim()
        val confirmPassword = binding.editConfirmPassword.text.toString().trim()

        if (name.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (phone?.isNotEmpty() == true) {
                    if (phone.length == 11) {
                        if (password.isNotEmpty()) {
                            if (confirmPassword.isNotEmpty() && confirmPassword == password) {

                                hideKeyboard()

                                registerUser(name, email, phone, password)

                            } else {

                                showBottomSheet(message = getString(R.string.error_txt_confirmation))
                            }
                        } else {

                            showBottomSheet(message = getString(R.string.error_txt_empty_password))
                        }
                    } else {
                        showBottomSheet(message = getString(R.string.error_invalid_phone))
                    }
                } else {

                    showBottomSheet(message = getString(R.string.error_txt_empty_phone))
                }
            } else {

                showBottomSheet(message = getString(R.string.error_txt_empty_email))
            }
        } else {

            showBottomSheet(message = getString(R.string.error_txt_empty_name))
        }
    }

    private fun registerUser(name: String, email: String, phone: String, password: String) {

        registerViewModel.register(name, email, phone, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    stateView.data?.let{saveProfile(it)
                    }
                }

                else -> {
                    binding.progressBar.isVisible = false

                    Log.i("TAG", stateView.message ?: "")

                    showBottomSheet(
                        message = getString(
                            FireBaseHelper.validateError(stateView.message ?: "")
                        )
                    )
                }
            }
        }

    }

    private fun saveProfile(user: User) {
       profileViewModel.saveProfile(user).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    val navOptions: NavOptions =
                        NavOptions.Builder()
                            .setPopUpTo(R.id.registerFragment, true).build()

                    findNavController().navigate(R.id.action_global_homeFragment, null, navOptions)
                }

                else -> {
                    binding.progressBar.isVisible = false

                    Log.i("TAG", stateView.message ?: "")

                        showBottomSheet(
                            message = getString(
                            FireBaseHelper.validateError(stateView.message ?: "")
                        )
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}