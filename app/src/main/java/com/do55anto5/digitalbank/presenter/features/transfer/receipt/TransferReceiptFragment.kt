package com.do55anto5.digitalbank.presenter.features.transfer.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.Transfer
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.FragmentTransferReceiptBinding
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.do55anto5.digitalbank.util.GetMask
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferReceiptFragment : Fragment() {

    private var _binding: FragmentTransferReceiptBinding? = null
    private val binding get() = _binding!!

    private val transferReceiptViewModel: TransferReceiptViewModel by viewModels()

    private val args: TransferReceiptFragmentArgs by navArgs()

    private val picassoTag = "picassoTag"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar, args.homeAsUpEnabled)

        initListeners()

        getTransfer(args.idTransfer)
    }

    private fun getTransfer(id: String) {
        transferReceiptViewModel.getTransfer(id).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    stateView.data?.let { transfer ->
                        val userId = if (transfer.senderUserId == FireBaseHelper.getUserId()) {
                            transfer.recipientUserId
                        } else {
                            transfer.senderUserId
                        }
                        getProfile(userId)
                        configTransfer(transfer)
                    }
                }
                is StateView.Error -> {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun getProfile(id: String) {
        transferReceiptViewModel.getProfile(id).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    stateView.data?.let { configProfile(it) }
                }
                is StateView.Error -> {
                    Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun configTransfer(transfer: Transfer) {
        with(binding){


            txtSentOrReceived.text = if (transfer.senderUserId == FireBaseHelper.getUserId()) {
                getString(R.string.transfer_receipt_fragment_txt_transfer_recipient)
            } else {
                getString(R.string.transfer_receipt_fragment_txt_transfer_sender)
            }

                txtCodeTransaction.text = transfer.id
            txtDateTransaction.text = GetMask.getFormattedDate(
                transfer.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
            txtAmountTransaction.text = getString(
                R.string.home_fragment_currency_symbol, GetMask.getFormattedValue(transfer.amount))
        }
    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun configProfile(user: User) {
        if (user.image.isNotEmpty()) {
            Picasso.get()
                .load(user.image)
                .fit().centerCrop()
                .tag(picassoTag)
                .into(binding.userImage, object : Callback {
                    override fun onSuccess() {
                        binding.progressImage.isVisible = false
                        binding.userImage.isVisible = true
                    }

                    override fun onError(e: java.lang.Exception?) {
                        Toast.makeText(requireContext(), R.string.generic_error, Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        } else {
            binding.progressImage.isVisible = false
            binding.userImage.isVisible = true
            binding.userImage.setImageResource(R.drawable.ic_user_place_holder)
        }

        binding.recipientName.text = user.name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Picasso.get().cancelTag(picassoTag)
        _binding = null
    }
}