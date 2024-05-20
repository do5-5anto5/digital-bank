package com.do55anto5.digitalbank.data.model

import android.os.Parcelable
import com.google.firebase.database.FirebaseDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transfer(
    var id: String = "",
    var recipientUserId: String = "",
    var senderUserId: String = "",
    var date: Long = 0L,
    var amount: Float = 0f
) : Parcelable {
    init {
        this.id = FirebaseDatabase.getInstance().reference.push().key ?: ""
    }
}
