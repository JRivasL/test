package com.example.android.roomwordssample.retrofit.model

data class ResponseUsers(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: ArrayList<User>
)
