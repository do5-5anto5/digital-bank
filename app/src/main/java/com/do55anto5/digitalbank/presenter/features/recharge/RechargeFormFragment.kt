package com.do55anto5.digitalbank.presenter.features.recharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentRechargeFormBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet

class RechargeFormFragment : BaseFragment() {

    private var _binding: FragmentRechargeFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar, light = true)

        initListeners()
    }

    private fun initListeners() {
        with(binding) {

            btnContinue.setOnClickListener {
                validateRecharge()
            }
        }

    }

    private fun validateRecharge() {
        val amount = binding.editAmount.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()

        if (amount.isNotEmpty()) {
            if (phone.isNotEmpty()) {
                if (phone.length == 11) {

                    hideKeyboard()

                    Toast.makeText(requireContext(), R.string.mock_message, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    showBottomSheet(message = getString(R.string.error_invalid_phone))
                }
            } else {
                showBottomSheet(message = getString(R.string.error_txt_empty_phone))
            }
        } else {
            showBottomSheet(message = getString(R.string.deposit_form_fragment_bottom_sheet_amount_empty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
