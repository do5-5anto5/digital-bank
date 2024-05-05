package com.do55anto5.digitalbank.util

import com.google.firebase.auth.FirebaseAuth

class FireBaseHelper {

    companion object {
        fun isAuthenticated() = FirebaseAuth.getInstance().currentUser != null
    }

}