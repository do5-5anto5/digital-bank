package com.do55anto5.digitalbank.presenter.features.transfer.confirm_transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentConfirmTransferBinding
import com.do55anto5.digitalbank.util.GetMask
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmTransferFragment : Fragment() {

    private var _binding: FragmentConfirmTransferBinding? = null
    private val binding get() = _binding!!

    private val confirmTransferViewModel: ConfirmTransferViewModel by viewModels()

    private val args: ConfirmTransferFragmentArgs by navArgs()

    private val picassoTag = "picassoTag"

    private var userBalance = 0f

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

        initToolbar(binding.toolbar, true)

        configData()

        getBalance()

        initListeners()

    }

    private fun getBalance() {
        confirmTransferViewModel.getBalance().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    userBalance = stateView.data ?: 0f
                }

                else -> {
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener {
            if (userBalance >= args.amount) {
                Toast.makeText(requireContext(), "Mock confirmation message", Toast.LENGTH_SHORT).show()
            } else {
                showBottomSheet(message  = getString(R.string.confirm_fragment_bottom_sheet_insufficient_funds))
            }
        }
    }

    private fun configData() {
        if (args.user.image.isNotEmpty()) {
            Picasso.get()
                .load(args.user.image)
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

        binding.recipientName.text = args.user.name
        binding.txtTransferAmount.text =
            getString(
                R.string.home_fragment_currency_symbol,
                GetMask.getFormattedValue(args.amount)
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Picasso.get().cancelTag(picassoTag)
        _binding = null
    }
}