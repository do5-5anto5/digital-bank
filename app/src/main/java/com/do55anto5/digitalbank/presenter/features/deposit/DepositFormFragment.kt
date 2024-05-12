package com.do55anto5.digitalbank.presenter.features.deposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.data.enum.TransactionType
import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.databinding.FragmentDepositFormBinding
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepositFormFragment : Fragment() {

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
        binding.btnContinue.setOnClickListener { validateDeposit() }
    }

    private fun validateDeposit() {
        val amount = binding.editAmount.text.toString().trim()

        if (amount.isNotEmpty()) {

            val deposit = Deposit(amount = amount.toFloat())

            saveDeposit(deposit)

        } else {
            Toast.makeText(
                requireContext(), R.string.deposit_form_fragment_toast_deposit_empty,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveDeposit(deposit: Deposit) {
        depositViewModel.saveDeposit(deposit).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = false
                }

                is StateView.Success -> {
                    stateView.data?.let{ saveTransaction(it) }
                }

                is StateView.Error -> {
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
                    val action = DepositFormFragmentDirections
                        .actionDepositFormFragmentToDepositReceiptFragment(deposit)

                    findNavController().navigate(action)
                }

                is StateView.Error -> {
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