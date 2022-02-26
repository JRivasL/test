package com.example.android.roomwordssample.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.roomwordssample.R
import com.example.android.roomwordssample.databinding.ActivityListUsersBinding
import com.example.android.roomwordssample.listener.NextListener
import com.example.android.roomwordssample.listener.ShowDetail
import com.example.android.roomwordssample.retrofit.RetrofitService
import com.example.android.roomwordssample.retrofit.model.ResponseUsers
import com.example.android.roomwordssample.retrofit.model.User
import com.example.android.roomwordssample.user.UserDetail
import com.example.android.roomwordssample.users.adapter.UsersAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListUsers : AppCompatActivity(), NextListener, ShowDetail {
    private var page = 1
    lateinit var usersAdapter: UsersAdapter
    private lateinit var _binding: ActivityListUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListUsersBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initList()
        getData()
    }
    private fun initList() {
        val layoutManager = LinearLayoutManager(this)

        _binding.rvUsers.layoutManager = layoutManager
        usersAdapter = UsersAdapter(ArrayList(), this, this)
        _binding.rvUsers.adapter = usersAdapter
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.getInstance().getUsers(page.toString())
            withContext(Dispatchers.Main) {

                validateResponse(response)

            }
        }
    }

    private fun validateResponse(response: ResponseUsers) {
        if(response.data.isNotEmpty()){
            usersAdapter.addData(response.data)
        } else{
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
        }
    }

    override fun onNext() {
        page++
        getData()
    }

    override fun onTap(user: User) {
        val intent = Intent(this, UserDetail::class.java)
        intent.putExtra("user", user.id)
        startActivity(intent)
    }
}