package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.R
import com.leobank.databinding.FragmentOthersBinding


class OthersFragment : Fragment() {
    private lateinit var binding:FragmentOthersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOthersBinding.inflate(inflater,container,false)
        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_othersFragment_to_profilesFragment)
        }

        return binding.root
    }


}