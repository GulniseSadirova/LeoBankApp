package com.leobank

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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
        itemId = arguments?.getString("id")?.toIntOrNull() ?: 0

        viewModel.fetchProducts()

        viewModel.items.observe(viewLifecycleOwner) { productList ->
            val item = productList.find { it.itemId == itemId }
            if (item != null) {
                binding.textTitle.text = item.title
                binding.textExplanation.text = item.explanation
                binding.textPrice.text = item.price.toString()
                Glide.with(this)
                    .load(item.imageUrl)
                    .into(binding.image)
            } else {
                Toast.makeText(requireContext(), "Ürün bulunamadı.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_singleItemFragment_to_mainFragment)
        }
    }
}
