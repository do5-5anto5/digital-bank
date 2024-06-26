package com.do55anto5.digitalbank.presenter.features.deposit

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
import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.databinding.FragmentDepositFormBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.MoneyTextWatcher
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositFormFragment : BaseFragment() {

    private var _binding: FragmentDepositFormBinding? = null
    private val binding get() = _binding!!

    private val depositViewModel: DepositViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar, light = true)

        initListeners()
    }

    private fun initListeners() {
        with(binding.editAmount) {
            addTextChangedListener(MoneyTextWatcher(this))

            addTextChangedListener {
                if (MoneyTextWatcher.getValueUnMasked(this) > 99999.99f) {
                    this.setText(R.string.value_zero_mock)
                }
            }

            doAfterTextChanged {
                text?.length?.let { this.setSelection(it) }
            }
        }

        binding.btnContinue.setOnClickListener { validateDeposit() }
    }

    private fun validateDeposit() {
        val amount = MoneyTextWatcher.getValueUnMasked(binding.editAmount)

        if (amount > 0f) {

            val deposit = Deposit(amount = amount)

            hideKeyboard()

            saveDeposit(deposit)

        } else {
            showBottomSheet(message = getString(R.string.deposit_form_fragment_message_deposit_empty))
        }
    }

    private fun saveDeposit(deposit: Deposit) {
        depositViewModel.saveDeposit(deposit).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    stateView.data?.let { saveTransaction(it) }
                }

                else -> {
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun saveTransaction(deposit: Deposit) {
        val transaction = Transaction(
            id = deposit.id,
            operation = TransactionOperation.DEPOSIT,
            date = deposit.date,
            amount = deposit.amount,
            type = TransactionType.CASH_IN
        )

        depositViewModel.saveTransaction(transaction).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    val action = MainGraphDirections
                        .actionGlobalDepositReceiptFragment(deposit.id, false)

                    val navOptions: NavOptions =
                        NavOptions.Builder()
                            .setPopUpTo(R.id.depositFormFragment, true)
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