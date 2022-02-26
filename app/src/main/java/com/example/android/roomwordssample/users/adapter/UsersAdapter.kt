/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.databinding.ItemUserBinding
import com.example.android.roomwordssample.listener.NextListener
import com.example.android.roomwordssample.listener.ShowDetail
import com.example.android.roomwordssample.retrofit.model.User

class UsersAdapter(private var list: ArrayList<User>, private val nextListener: NextListener, private val detail: ShowDetail) :
    RecyclerView.Adapter<ViewHolderUser>() {

    fun addData(users: ArrayList<User>) {
        this.list.addAll(users)
        notifyItemRangeInserted(this.list.size - users.size, this.list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolderUser(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val user = list[position]
        (user.first_name + " " + user.last_name).also { holder.binding.name.text = it }
        holder.binding.email.text = user.email
        if (position == itemCount - 1 && position != 0) {
            nextListener.onNext()
        }
        holder.binding.root.setOnClickListener{
            detail.onTap(user)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ViewHolderUser(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)