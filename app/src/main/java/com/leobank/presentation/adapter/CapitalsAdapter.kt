package com.leobank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leobank.databinding.ItemCapitalsBinding
import com.leobank.databinding.ItemUsersBinding
import com.leobank.domain.Capitals
import com.leobank.domain.User

class CapitalsAdapter(private val capitals:List<Capitals>):RecyclerView.Adapter<CapitalsAdapter.CapitalViewHolder>() {

    inner class CapitalViewHolder(private val binding:ItemCapitalsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(capitals: Capitals) {
            binding.capitals.text = capitals.capitals
            binding.percent.text = capitals.percent

            Glide.with(binding.root)
                .load(capitals.imageUrl)
                .into(binding.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapitalViewHolder {
        val binding = ItemCapitalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CapitalViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return capitals.size
    }

    override fun onBindViewHolder(holder: CapitalViewHolder, position: Int) {
        val capital = capitals[position]
        holder.bind(capital)
    }


}