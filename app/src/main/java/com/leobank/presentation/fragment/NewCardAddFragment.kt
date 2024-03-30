package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.R
import com.leobank.databinding.FragmentNewCardAddBinding


class NewCardAddFragment : Fragment() {
    private lateinit var binding:FragmentNewCardAddBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNewCardAddBinding.inflate(inflater,container,false)
        back()
        bttnSend()
        return binding.root
    }
    private fun back(){
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_newCardAddFragment_to_sendToCardFragment)
        }
    }
    private fun bttnSend(){
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_newCardAddFragment_to_decreaseCardFragment)

        }
    }


}