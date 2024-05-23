package com.do55anto5.digitalbank.presenter.features.recharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.MainGraphDirections
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.data.enum.TransactionType
import com.do55anto5.digitalbank.data.model.Recharge
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.databinding.FragmentRechargeFormBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.MoneyTextWatcher
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeFormFragment : BaseFragment() {

    private var _binding: FragmentRechargeFormBinding? = null
    private val binding get() = _binding!!

    private val rechargeViewModel: RechargeViewModel by viewModels()

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

        with(binding.editAmount) {addTextChangedListener(MoneyTextWatcher(this))

            addTextChangedListener {
                if (MoneyTextWatcher.getValueUnMasked(this) > 100) {
                    this.setText(R.string.value_zero_mock)
                }
            }

            doAfterTextChanged {
                text?.length?.let { this.setSelection(it) }
            }
        }

        binding.btnContinue.setOnClickListener {
            binding.btnContinue.isEnabled = false
            validateRecharge()
        }

    }

    private fun validateRecharge() {
        val amount = MoneyTextWatcher.getValueUnMasked(binding.editAmount)
        val phone = binding.editPhone.unMaskedText

        if (amount >= 10f) {
            if (phone?.isNotEmpty() == true) {
                if (phone.length == 11) {

                    binding.progressBar.isVisible = true

                    hideKeyboard()

                    val recharge = Recharge(
                        amount = amount,
                        number = phone,
                    )
                   saveRecharge(recharge)

                } else {
                    binding.btnContinue.isEnabled = true
                    val length = phone.length
                    showBottomSheet(message = length.toString())
                }
            } else {
                binding.btnContinue.isEnabled = true
                showBottomSheet(message = getString(R.string.error_txt_empty_phone))
            }
        } else {
            binding.btnContinue.isEnabled = true
            showBottomSheet(message = getString(R.string.deposit_form_fragment_bottom_sheet_amount_invalid))
        }
    }

    private fun saveRecharge(recharge: Recharge) {
        rechargeViewModel.saveRecharge(recharge).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    stateView.data?.let{ saveTransaction(it) }
                }

                else -> {
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun saveTransaction(recharge: Recharge) {
        val transaction = Transaction(
            id = recharge.id,
            operation = TransactionOperation.RECHARGE,
            date = recharge.date,
            amount = recharge.amount,
            type = TransactionType.CASH_OUT
        )

        rechargeViewModel.saveTransaction(transaction).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    val action = MainGraphDirections
                        .actionGlobalRechargeReceiptFragment(recharge.id, false)

                    val navOptions: NavOptions =
                        NavOptions.Builder()
                            .setPopUpTo(R.id.rechargeFormFragment, true)
                            .build()

                    findNavController().navigate(action, navOptions)
                }

                else -> {
                    showBottomSheet(message = stateView.message)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
