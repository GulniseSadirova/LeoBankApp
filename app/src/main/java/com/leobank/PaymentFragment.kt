package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPaymentBinding.inflate(inflater,container,false)
        back()
        return binding.root
    }
    private fun back(){
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_mainFragment)
        }
    }



}