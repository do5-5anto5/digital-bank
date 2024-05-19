package com.do55anto5.digitalbank.presenter.features.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.Deposit
import com.do55anto5.digitalbank.databinding.FragmentConfirmTransferBinding
import com.do55anto5.digitalbank.databinding.FragmentDepositReceiptBinding
import com.do55anto5.digitalbank.util.GetMask
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmTransferFragment : Fragment() {

    private var _binding: FragmentConfirmTransferBinding? = null
    private val binding get() = _binding!!

//    private val args: FragmentConfirmTransferBinding by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar,  true)

//        getDeposit()

        initListeners()

    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun configData(deposit: Deposit) {
        with(binding){

//        txtCodeTransaction.text = deposit.id
//        txtDateTransaction.text = GetMask.getFormattedDate(
//            deposit.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
//        txtAmountTransaction.text = getString(
//            R.string.home_fragment_currency_symbol, GetMask.getFormattedValue(deposit.amount))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}