package com.do55anto5.digitalbank.presenter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.Wallet
import com.do55anto5.digitalbank.databinding.FragmentHomeBinding
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.do55anto5.digitalbank.util.GetMask
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

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
        getWallet()
    }

    private fun getWallet() {
        homeViewModel.getWallet().observe(viewLifecycleOwner) { stateView ->
            when(stateView) {

                is StateView.Loading -> {
                }

                is StateView.Success -> {

                    stateView.data?.let { showBalance(it) }


                }

                else -> {

                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun showBalance(wallet: Wallet) {
        binding.txtBalance.text = getString(
            R.string.currency_symbol, GetMask.getFormattedValue(wallet.balance))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}