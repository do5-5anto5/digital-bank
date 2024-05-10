package com.do55anto5.digitalbank.presenter.features.deposit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentDepositFormBinding
import com.do55anto5.digitalbank.util.initToolbar

class DepositFormFragment : Fragment() {

    private var _binding: FragmentDepositFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentDepositFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar, light = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}