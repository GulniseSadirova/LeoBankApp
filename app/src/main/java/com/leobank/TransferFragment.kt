package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leobank.databinding.FragmentTransferBinding


class TransferFragment : Fragment() {
    private lateinit var binding: FragmentTransferBinding
    private lateinit var transferViewModel: TransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferBinding.inflate(inflater, container, false)
        back()
        setupClickListeners()


        transferViewModel = ViewModelProvider(requireActivity()).get(TransferViewModel::class.java)
        transferViewModel.initSharedPreferences(requireContext())

        return binding.root
    }

    private fun back() {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_transferFragment_to_anotherCardIncreaseFragment)
        }
    }

    private fun setupClickListeners() {
        binding.buttonSend.setOnClickListener {
            val enteredAmount = binding.editTextManat.text.toString().toDoubleOrNull()
            if (enteredAmount == null) {
                Toast.makeText(requireContext(), "Düzgün daxil edin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            transferViewModel.updateTotalAmount(enteredAmount)
            val newTotalAmount = transferViewModel.transferAmount.value ?: 0.0
            Toast.makeText(requireContext(), "$enteredAmount Balansınız uğurla artırıldı", Toast.LENGTH_SHORT).show()


            findNavController().navigate(R.id.action_transferFragment_to_mainFragment)
        }
    }
}



