package com.do55anto5.digitalbank.presenter.features.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentTransferFormBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.MoneyTextWatcher
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFormFragment : BaseFragment() {

    private var _binding: FragmentTransferFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferFormBinding.inflate(inflater, container, false)
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

        binding.btnContinue.setOnClickListener { validateAmount() }
    }

    private fun validateAmount() {
        val amount = MoneyTextWatcher.getValueUnMasked(binding.editAmount)

        if (amount > 0f) {

            hideKeyboard()

            //action

        } else {
            showBottomSheet(message = getString(R.string.transfer_form_fragment_message_deposit_empty))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}