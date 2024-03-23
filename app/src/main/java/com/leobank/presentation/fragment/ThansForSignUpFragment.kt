package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.R
import com.leobank.databinding.FragmentThansForSignUpBinding


class ThansForSignUpFragment : Fragment() {
    private lateinit var binding: FragmentThansForSignUpBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentThansForSignUpBinding.inflate(inflater,container,false)
        click()
        return binding.root
    }
    private fun click(){
        findNavController().navigate(R.id.action_thansForSignUpFragment_to_numberAddFragment)
    }



}