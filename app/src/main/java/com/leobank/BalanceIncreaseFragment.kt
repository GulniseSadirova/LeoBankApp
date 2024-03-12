package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentBalanceIncreaseBinding


class BalanceIncreaseFragment : Fragment() {
    private lateinit var binding: FragmentBalanceIncreaseBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBalanceIncreaseBinding.inflate(inflater,container,false)
        balanceİncreaseBack()
        anotherCardIncrease()
        cashMoney()
        return binding.root
    }
    private fun balanceİncreaseBack(){
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_balanceIncreaseFragment_to_mainFragment)
        }
    }
    private fun anotherCardIncrease(){
        binding.imageAnotherCard.setOnClickListener {
            findNavController().navigate(R.id.action_balanceIncreaseFragment_to_anotherCardIncreaseFragment)
        }

    }
    private fun cashMoney(){
        binding.imageNagd.setOnClickListener {

        }

    }



}