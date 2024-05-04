package com.do55anto5.digitalbank.presenter.auth.recover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.FragmentLoginBinding
import com.do55anto5.digitalbank.databinding.FragmentRecoverBinding
import com.do55anto5.digitalbank.util.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        initListeners()
    }

    private fun initListeners() {

        binding.sendBtn.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()

        if (email.isNotEmpty()) {
                Toast.makeText(requireContext(), "Sending link to recover password", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Email field is empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}