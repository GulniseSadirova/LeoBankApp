package com.leobank.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leobank.domain.Spending
import com.leobank.databinding.ItemSpendingBinding

class Adapter(private var listener: OnItemClickListener):RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Spending)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    private val diffCallback=object :DiffUtil.ItemCallback<Spending>(){
        override fun areItemsTheSame(oldItem: Spending, newItem: Spending): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Spending, newItem: Spending): Boolean {
           return oldItem==newItem
        }

    }
    private val diffUtil=AsyncListDiffer(this,diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding=ItemSpendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(diffUtil.currentList[position])
    }
    inner class ProductViewHolder(private val binding:ItemSpendingBinding):
        RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener {
                    val position=adapterPosition
                    if (position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(diffUtil.currentList[position])

                    }
                }
            }



            fun bind(item: Spending) {
                binding.title.text=item.title
                binding.explanation.text=item.explanation
                binding.price.text="$${item.price}"
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .into(binding.image)

            }

    }
    fun submitList(items:List<Spending>){
        diffUtil.submitList(items)
    }
}