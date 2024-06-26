package com.do55anto5.digitalbank.presenter.features.bankStatement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.MainGraphDirections
import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.databinding.FragmentBankStatementBinding
import com.do55anto5.digitalbank.presenter.home.TransactionAdapter
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankStatementFragment : Fragment() {

    private var _binding: FragmentBankStatementBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterTransaction: TransactionAdapter

    private val bankStatementViewModel: BankStatementViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankStatementBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        configRecyclerView()

        getTransactions()
    }

    private fun configRecyclerView() {
        adapterTransaction = TransactionAdapter(requireContext()) { transaction ->
            when (transaction.operation) {
                TransactionOperation.DEPOSIT -> {
                    val action = MainGraphDirections
                        .actionGlobalDepositReceiptFragment(transaction.id, true)

                    findNavController().navigate(action)
                }

                TransactionOperation.RECHARGE -> {
                    val action = MainGraphDirections
                        .actionGlobalRechargeReceiptFragment(transaction.id, true
                        )

                    findNavController().navigate(action)
                }

                TransactionOperation.TRANSFER -> {
                    val action = MainGraphDirections
                        .actionGlobalTransferReceiptFragment(transaction.id, true)
                    findNavController().navigate(action)
                }

                else -> {
                }
            }
        }

        with(binding.rvTransactions) {
            setHasFixedSize(true)
            adapter = adapterTransaction
        }
    }

    private fun getTransactions() {
        bankStatementViewModel.getTransactions().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false

                    binding.txtEmptyTransactionsList.isVisible = stateView.data?.isEmpty() == true

                    adapterTransaction.submitList(stateView.data?.reversed())
                }

                else -> {
                    binding.progressBar.isVisible = false
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