package com.do55anto5.digitalbank.data.model

import com.google.firebase.database.FirebaseDatabase

data class Deposit(
    var id : String = "",
    var date: Long = 0,
    var amount: Float = 0f
) {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
