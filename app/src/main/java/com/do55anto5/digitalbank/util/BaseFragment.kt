package com.do55anto5.digitalbank.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun hideKeyboard() {
        val view = activity?.currentFocus
        view?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}