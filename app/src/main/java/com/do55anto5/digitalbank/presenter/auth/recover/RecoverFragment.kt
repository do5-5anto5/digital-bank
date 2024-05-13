package com.do55anto5.digitalbank.presenter.auth.recover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentRecoverBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverFragment : BaseFragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get()= _binding!!

    private val recoverViewModel: RecoverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() {

        binding.sendBtn.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {

            hideKeyboard()

            recoverAccount(email)

        } else {
            showBottomSheet(message = getString(R.string.error_txt_empty_email))
        }
    }

    private fun recoverAccount(email: String) {

        recoverViewModel.recover(email).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false

                    showBottomSheet(
                        message = getString(R.string.recover_fragment_bottom_sheet_success_recover))

                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false

                    showBottomSheet(message = getString(
                        FireBaseHelper.validateError(stateView.message ?: "")))
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}