package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentSmsAddBinding


class smsAddFragment : Fragment() {
    private lateinit var binding: FragmentSmsAddBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSmsAddBinding.inflate(inflater,container,false)
        click()
        login()
        return binding.root


    }
    private fun click(){
        binding.bttnIrali.setOnClickListener {
            findNavController().navigate(R.id.action_smsAddFragment_to_emailAddFragment)
        }

    }
    private fun login(){
        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_smsAddFragment_to_pinCodeFragment2)

        }
    }


}