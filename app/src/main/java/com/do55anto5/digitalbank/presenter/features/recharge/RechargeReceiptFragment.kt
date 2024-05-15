package com.do55anto5.digitalbank.presenter.features.recharge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.Recharge
import com.do55anto5.digitalbank.databinding.FragmentRechargeReceiptBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.GetMask
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RechargeReceiptFragment : BaseFragment() {

    private var _binding: FragmentRechargeReceiptBinding? = null
    private val binding get() = _binding!!

    private val args : RechargeReceiptFragmentArgs by navArgs()

    private val rechargeReceiptViewModel: RechargeReceiptViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar, args.homeAsUpEnabled)

        getRecharge()

        initListeners()
    }


    private fun getRecharge() {
        rechargeReceiptViewModel.getRecharge(args.idRecharge).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    stateView.data?.let { configData(it) }
                }
                is StateView.Error -> {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun configData(recharge: Recharge) {
        with(binding){

            txtCodeTransaction.text = recharge.id
            txtDateTransaction.text = GetMask.getFormattedDate(
                recharge.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
            txtAmountTransaction.text = getString(
                R.string.home_fragment_currency_symbol, GetMask.getFormattedValue(recharge.amount))
            txtPhoneNumber.text = recharge.number
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}