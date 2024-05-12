package com.do55anto5.digitalbank.presenter.features.deposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentDepositReceiptBinding
import com.do55anto5.digitalbank.util.GetMask

class DepositReceiptFragment : Fragment() {

    private var _binding: FragmentDepositReceiptBinding? = null
    private val binding get() = _binding!!

    private val args: DepositReceiptFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configData()

        initListeners()

    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun configData() {
        with(binding){

        txtCodeTransaction.text = args.deposit.id
        txtDateTransaction.text = GetMask.getFormattedDate(
            args.deposit.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
        txtAmountTransaction.text = getString(
            R.string.home_fragment_currency_symbol, GetMask.getFormattedValue(args.deposit.amount))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}