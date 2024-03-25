package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.R
import com.leobank.databinding.FragmentSendToCardBinding


class SendToCardFragment : Fragment() {
    private lateinit var binding: FragmentSendToCardBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSendToCardBinding.inflate(inflater,container,false)
        back()
        newCard()
        return binding.root
    }
    private fun back(){
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_sendToCardFragment_to_mainFragment)
        }
    }
    private fun newCard(){
        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.action_sendToCardFragment_to_newCardAddFragment)
        }
    }


}