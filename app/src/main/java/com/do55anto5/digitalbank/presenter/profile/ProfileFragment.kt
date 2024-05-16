package com.do55anto5.digitalbank.presenter.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.data.model.User
import com.do55anto5.digitalbank.databinding.FragmentProfileBinding
import com.do55anto5.digitalbank.util.BaseFragment
import com.do55anto5.digitalbank.util.FireBaseHelper
import com.do55anto5.digitalbank.util.StateView
import com.do55anto5.digitalbank.util.initToolbar
import com.do55anto5.digitalbank.util.showBottomSheet
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    private var user: User? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        configData()

        getProfile()

        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener { if (user != null) validateData() }
    }

    private fun checkCameraPermission() {

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                Toast.makeText(
                    requireContext(),
                    R.string.dialog_permission_granted_title, Toast.LENGTH_SHORT
                ).show()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    requireContext(),
                    R.string.dialog_permission_denied_title, Toast.LENGTH_SHORT
                ).show()
            }
        }

        showDialogPermissionDenied(
            permissionListener = permissionlistener,
            permission = android.Manifest.permission.CAMERA,
            message = getString(R.string.dialog_permission_camera_access_denied)
        )
    }

    private fun checkGalleryPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {

            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    requireContext(),
                    "Permission Denied", Toast.LENGTH_SHORT
                ).show()
            }
        }

        showDialogPermissionDenied(
            permissionListener = permissionlistener,
            permission = android.Manifest.permission.READ_EXTERNAL_STORAGE,
            message = getString(R.string.dialog_permission_gallery_access_denied)
        )
    }

    private fun showDialogPermissionDenied(
        permissionListener: PermissionListener,
        permission: String,
        message: String
    ) {
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedTitle(getText(R.string.dialog_permission_denied_title))
            .setDeniedMessage(message)
            .setDeniedCloseButtonText(getText(R.string.dialog_permission_denied_btn_close))
            .setGotoSettingButtonText(getText(R.string.dialog_permission_denied_btn_setting))
            .setPermissions(permission)
            .check()
    }

    private fun validateData() {
        val name = binding.editName.text.toString().trim()
        val phone = binding.editPhone.unMaskedText

        if (name.isNotEmpty()) {
            if (phone?.isNotEmpty() == true) {
                if (phone.length == 11) {

                    hideKeyboard()

                    user?.name = name
                    user?.phone = phone

                    saveProfile()

                } else {
                    showBottomSheet(message = getString(R.string.error_invalid_phone))
                }
            } else {

                showBottomSheet(message = getString(R.string.error_txt_empty_phone))
            }
        } else {

            showBottomSheet(message = getString(R.string.error_txt_empty_name))
        }
    }

    private fun getProfile() {
        profileViewModel.getProfile().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {

                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    stateView.data?.let { user = it }
                    configData()
                }

                else -> {
                    binding.progressBar.isVisible = false

                    Log.i("TAG", stateView.message ?: "")

                    showBottomSheet(
                        message = getString(
                            FireBaseHelper.validateError(stateView.message ?: "")
                        )
                    )
                }
            }
        }
    }

    private fun saveProfile() {

        user?.let {

            profileViewModel.saveProfile(it).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {

                    is StateView.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is StateView.Success -> {
                        binding.progressBar.isVisible = false

                        Toast.makeText(requireContext(),
                            R.string.profile_fragment_toast_save_profile_success,
                            Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        binding.progressBar.isVisible = false

                        Log.i("TAG", stateView.message ?: "")

                        showBottomSheet(
                            message = getString(
                                FireBaseHelper.validateError(stateView.message ?: "")
                            )
                        )
                    }
                }
            }
        }
    }

    private fun configData() {
        with(binding) {
            editName.setText(user?.name)
            editPhone.setText(user?.phone)
            editEmail.setText(user?.email)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}