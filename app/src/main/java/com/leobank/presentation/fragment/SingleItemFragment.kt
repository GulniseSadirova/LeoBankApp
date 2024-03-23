package com.leobank.presentation.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.leobank.R
import com.leobank.presentation.viewmodel.SingleItemViewModel
import com.leobank.databinding.FragmentSingleItemBinding


class SingleItemFragment : Fragment() {
    private lateinit var binding: FragmentSingleItemBinding
    private lateinit var viewModel: SingleItemViewModel
    private var itemId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SingleItemViewModel::class.java)
        itemId = arguments?.getInt("id") ?: 0
        viewModel.fetchProducts(itemId)
        Log.e(TAG, "onViewCreated: ${arguments?.getInt("id")}" )

        viewModel.items.observe(viewLifecycleOwner) { productList ->
            val item = productList.find { it.itemId == itemId }
            item?.let{
                binding.textTitle.text = item.title
                binding.textExplanation.text = item.explanation
                binding.textPrice.text = item.price.toString()
                Glide.with(this)
                    .load(item.imageUrl)
                    .into(binding.image)
            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_singleItemFragment_to_mainFragment)
        }
    }
}
