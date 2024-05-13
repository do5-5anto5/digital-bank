package com.do55anto5.digitalbank.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.enum.TransactionOperation
import com.do55anto5.digitalbank.data.enum.TransactionType
import com.do55anto5.digitalbank.data.model.Transaction
import com.do55anto5.digitalbank.databinding.FragmentHomeBinding
import com.do55anto5.digitalbank.util.GetMask
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapterTransaction: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configRecyclerView()

        getTransactions()

        initListeners()
    }

    private fun initListeners() {
        binding.cardDeposit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_depositFormFragment)
        }
    }

    private fun configRecyclerView() {
        adapterTransaction = TransactionAdapter(requireContext()) { transaction ->
            when (transaction.operation) {
                TransactionOperation.DEPOSIT -> {
                    val action = HomeFragmentDirections
                        .actionHomeFragmentToDepositReceiptFragment(transaction.id, true)

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
        homeViewModel.getTransactions().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false

                    adapterTransaction.submitList(stateView.data?.reversed()?.take(6))

                    showBalance(stateView.data ?: emptyList())
                }

                else -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun showBalance(transactions: List<Transaction>) {
        var cashIn = 0f
        var cashOut = 0f

        transactions.forEach { transaction ->
            if (transaction.type == TransactionType.CASH_IN) {
                cashIn += transaction.amount
            } else {
                cashOut += transaction.amount
            }
        }

        binding.txtBalance.text = getString(
            R.string.home_fragment_currency_symbol,
            GetMask.getFormattedValue(cashIn - cashOut)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}