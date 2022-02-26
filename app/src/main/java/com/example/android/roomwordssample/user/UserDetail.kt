package com.example.android.roomwordssample.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.roomwordssample.R
import com.example.android.roomwordssample.databinding.ActivityListUsersBinding
import com.example.android.roomwordssample.databinding.ActivityUserDetailBinding
import com.example.android.roomwordssample.retrofit.RetrofitService
import com.example.android.roomwordssample.retrofit.model.ResponseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetail : AppCompatActivity() {

    private lateinit var _binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        getData()
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.getInstance().getUser(intent.extras!!.getString("user","1"))
            withContext(Dispatchers.Main) {

                validateResponse(response)

            }
        }
    }

    private fun validateResponse(response: ResponseUser) {
        (response.data.first_name + " " + response.data.last_name).also { _binding.name.text = it }
        _binding.email.text = response.data.email
    }
}