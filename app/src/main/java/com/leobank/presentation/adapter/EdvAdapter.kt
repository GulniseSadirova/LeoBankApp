package com.leobank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leobank.domain.Edv
import com.leobank.databinding.ItemEdvBinding

class EdvAdapter(private var listener: OnItemClickListener) : RecyclerView.Adapter<EdvAdapter.ProductViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(item: Edv)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    private val diffCallback = object : DiffUtil.ItemCallback<Edv>() {
        override fun areItemsTheSame(oldItem: Edv, newItem: Edv): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Edv, newItem: Edv): Boolean {
            return oldItem == newItem
        }
    }
    private val diffUtil = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemEdvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(diffUtil.currentList[position])
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    inner class ProductViewHolder(private val binding: ItemEdvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(diffUtil.currentList[position])
                }
            }
        }

        fun bind(item: Edv) {
            binding.txtEdv.text = item.edv.toString()
            binding.txtMebleg.text = item.mebleg.toString()
            binding.ficsalD.text = item.ficsalİd
        }
    }

    fun submitList(items: List<Edv>) {
        diffUtil.submitList(items)
    }
}