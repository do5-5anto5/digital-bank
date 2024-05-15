package com.do55anto5.digitalbank.presenter.features.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.do55anto5.digitalbank.databinding.FragmentTransferUserListBinding
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferUserListFragment : Fragment() {

    private var _binding: FragmentTransferUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterTransferUser: TransferUserAdapter

    private val transferUserListViewModel: TransferUserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar, light = true)

        initRecyclerView()

        getProfilesList()
    }

    private fun getProfilesList() {
        transferUserListViewModel.getProfilesList().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false

                    adapterTransferUser.submitList(stateView.data)
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showBottomSheet(message = stateView.message)
                }
            }
        }
    }

    private fun initRecyclerView() {

        adapterTransferUser = TransferUserAdapter { selectedUser ->
            Toast.makeText(requireContext(), selectedUser.name, Toast.LENGTH_SHORT).show()
        }

        with(binding.rvUserList) {
            setHasFixedSize(true)
            adapter = adapterTransferUser
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}