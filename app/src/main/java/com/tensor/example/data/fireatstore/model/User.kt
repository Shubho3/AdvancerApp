package com.tensor.example.data.fireatstore.model

import com.google.firebase.Timestamp

data class User(
    var id: String? = null,
    var email: String? = null,
    var password: String? = null,
    var image: String? = null,
    var userName: String? = null,
    var bio: String? = null,
    var date: Timestamp? = null,
)