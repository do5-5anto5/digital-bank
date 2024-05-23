package com.do55anto5.digitalbank.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.do55anto5.digitalbank.R
import com.do55anto5.digitalbank.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar,
                         homeAsUpEnabled: Boolean = true,
                         light: Boolean = false) {
    val iconBack = if (light) R.drawable.ic_back_white else R.drawable.ic_back

    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(iconBack)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}

fun Fragment.showBottomSheet(
    title: Int? = null,
    titleButton: Int? = null,
    message: String?,
    onClick: () -> Unit = {}
) {

    val bottomSheetDialog = BottomSheetDialog(requireContext())
    val bottomSheetBinding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    bottomSheetBinding.txtTitle.text = getString(title ?: R.string.bottom_sheet_txt_title)
    bottomSheetBinding.txtMessage.text = message ?: getString(R.string.generic_error)
    bottomSheetBinding.btnOk.text = getString(titleButton ?: R.string.bottom_sheet_btn_title)

    bottomSheetBinding.btnOk.setOnClickListener {
        bottomSheetDialog.dismiss()
        onClick() }

    bottomSheetDialog.setContentView(bottomSheetBinding.root)
    bottomSheetDialog.show()

}