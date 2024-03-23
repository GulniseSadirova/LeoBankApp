package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.R
import com.leobank.databinding.FragmentDetailedCardBinding


class DetailedCardFragment : Fragment() {
    private lateinit var binding: FragmentDetailedCardBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailedCardBinding.inflate(inflater,container,false)
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailedCardFragment_to_mainFragment)
        }
        return binding.root

    }

}