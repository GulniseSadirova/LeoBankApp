package com.leobank

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentSingleEdvBinding


class SingleEdvFragment : Fragment() {
    private lateinit var binding: FragmentSingleEdvBinding
    private lateinit var viewModel: SingleEdvViewModel
    private var itemId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleEdvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SingleEdvViewModel::class.java)
        itemId = arguments?.getInt("id") ?: 0
        viewModel.fetchProducts(itemId)
        Log.e(TAG, "onViewCreated: ${arguments?.getInt("id")}")

        viewModel.items.observe(viewLifecycleOwner) { productList ->
            val item = productList.find { it.itemId == itemId }
            item?.let {
                binding.textEdv.text = item.edv.toInt().toString()
                binding.textFicsalD.text = item.ficsalİd
                binding.textDate.text = item.date

            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_singleEdvFragment_to_edvFragment)
        }


    }
}