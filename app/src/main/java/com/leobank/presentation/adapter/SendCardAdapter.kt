package com.leobank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leobank.databinding.ItemSpendingBinding
import com.leobank.databinding.ItemUsersBinding
import com.leobank.domain.Spending
import com.leobank.domain.User

class SendCardAdapter(private val userList: List<User>) : RecyclerView.Adapter<SendCardAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.name.text = user.name
            Glide.with(binding.root)
                .load(user.imageUrl)
                .into(binding.image)
        }
    }
}