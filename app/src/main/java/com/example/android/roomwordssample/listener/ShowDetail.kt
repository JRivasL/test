package com.example.android.roomwordssample.listener

import com.example.android.roomwordssample.retrofit.model.User

interface ShowDetail {
    fun onTap(user: User)
}
