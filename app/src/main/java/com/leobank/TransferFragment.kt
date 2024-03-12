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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transferViewModel = ViewModelProvider(requireActivity()).get(TransferViewModel::class.java)

        transferViewModel.cardNumber.observe(viewLifecycleOwner) { cardNumber ->
            binding.txtKartNomre.text = cardNumber
        }

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

            transferViewModel.setTransferAmount(enteredAmount)


            findNavController().navigate(R.id.action_transferFragment_to_mainFragment)
        }
    }

}



