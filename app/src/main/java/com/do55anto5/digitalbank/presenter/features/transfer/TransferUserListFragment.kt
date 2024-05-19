package com.do55anto5.digitalbank.presenter.features.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.FragmentTransferUserListBinding
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransferUserListFragment : Fragment() {

    private var _binding: FragmentTransferUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterTransferUser: TransferUserAdapter

    private var profilesList: List<User> = listOf()

    private val transferUserListViewModel: TransferUserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

        configSearchView()
    }

    private fun configSearchView() {
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return if (newText.isNotEmpty()) {
                    val newList = profilesList.filter { it.name.contains(newText, true) }
                    adapterTransferUser.submitList(newList)
                    true

                } else {
                    adapterTransferUser.submitList(profilesList)
                    false
                }
            }


            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }
        })

        binding.searchView.setOnSearchViewListener(
            object : SimpleSearchView.SearchViewListener {
                override fun onSearchViewShown() {
                }

                override fun onSearchViewClosed() {
                    adapterTransferUser.submitList(profilesList)
                }

                override fun onSearchViewShownAnimation() {
                }

                override fun onSearchViewClosedAnimation() {
                }
            })
    }

    private fun getProfilesList() {
        transferUserListViewModel.getProfilesList().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    profilesList = stateView.data ?: emptyList()
                    adapterTransferUser.submitList(stateView.data)

                    binding.progressBar.isVisible = false
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
            val action = TransferUserListFragmentDirections
                .actionTransferUserListFragmentToTransferFormFragment(selectedUser, true)
            findNavController().navigate(action)
        }

        with(binding.rvUserList) {
            setHasFixedSize(true)
            adapter = adapterTransferUser
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}