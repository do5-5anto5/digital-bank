package com.do55anto5.digitalbank.util

import com.do55anto5.digitalbank.R
import com.google.firebase.auth.FirebaseAuth

class FireBaseHelper {

    companion object {
        fun isAuthenticated() = FirebaseAuth.getInstance().currentUser != null

        fun validateError(error: String): Int {
            return when {
                error.contains("The email address is badly formatted.") -> {
                    R.string.error_invalid_email
                }

                error.contains("The supplied auth credential is incorrect, malformed or has expired.") -> {
                    R.string.error_invalid_credentials
                }
                else -> {
                    R.string.generic_error
                }
            }
        }
    }

}