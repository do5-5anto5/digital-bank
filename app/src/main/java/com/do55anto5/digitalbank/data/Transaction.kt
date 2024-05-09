package com.do55anto5.digitalbank.data

import com.google.firebase.database.FirebaseDatabase

data class Transaction(
    var id: String = "",
    val description: String = "",
    val date: Long = 0,
    val value: Float = 0f
) {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}