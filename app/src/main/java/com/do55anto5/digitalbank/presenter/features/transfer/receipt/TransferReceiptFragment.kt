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
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.FragmentTransferReceiptBinding
import com.do55anto5.digitalbank.util.initToolbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferReceiptFragment : Fragment() {

    private var _binding: FragmentTransferReceiptBinding? = null
    private val binding get() = _binding!!

    private val confirmTransferViewModel: TransferReceiptViewModel by viewModels()

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

        initToolbar(binding.toolbar, true)

        initListeners()

    }

    private fun initListeners() {
        binding.btnContinue.setOnClickListener{ findNavController().popBackStack() }
    }

    private fun configData(user: User) {
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
//        binding.txtTransferAmount.text =
//            getString(
//                R.string.home_fragment_currency_symbol,
//                GetMask.getFormattedValue(args.amount)
//            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Picasso.get().cancelTag(picassoTag)
        _binding = null
    }
}