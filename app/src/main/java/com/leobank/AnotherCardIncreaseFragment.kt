package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentAnotherCardIncreaseBinding


class AnotherCardIncreaseFragment : Fragment() {
   private lateinit var binding: FragmentAnotherCardIncreaseBinding
    private lateinit var transferViewModel: TransferViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAnotherCardIncreaseBinding.inflate(inflater,container,false)
        setupCardCredentials()
        back()
        transferViewModel = ViewModelProvider(requireActivity()).get(TransferViewModel::class.java)

        binding.button.setOnClickListener {
            val cardNumber = binding.editTextCardNum.text.toString()
            transferViewModel.setCardNumber(cardNumber)

            findNavController().navigate(R.id.action_anotherCardIncreaseFragment_to_transferFragment)
        }
        return binding.root
    }
    private fun back(){
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_anotherCardIncreaseFragment_to_balanceIncreaseFragment)

        }
    }

    private fun setupCardCredentials() {
        binding.button.setOnClickListener {
            val cardNum = binding.editTextCardNum.text.toString()
            val mmYY = binding.editTextMMYY.text.toString()
            val cvv = binding.editTextcvv.text.toString()

            if (cardNum.length != 16 || mmYY.length != 4 || cvv.length != 3) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields correctly!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.action_anotherCardIncreaseFragment_to_transferFragment)

            }
        }
    }





}