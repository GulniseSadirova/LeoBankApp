package com.leobank.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.leobank.NotificationService
import com.leobank.R
import com.leobank.presentation.viewmodel.TransferViewModel
import com.leobank.databinding.FragmentTransferBinding


class TransferFragment : Fragment() {
    private lateinit var binding: FragmentTransferBinding
    private lateinit var transferViewModel: TransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferBinding.inflate(inflater, container, false)
        setupClickListeners()

        transferViewModel = ViewModelProvider(requireActivity()).get(TransferViewModel::class.java)
        transferViewModel.initSharedPreferences(requireContext())

        return binding.root
    }

    private fun setupClickListeners() {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_transferFragment_to_anotherCardIncreaseFragment)
        }

        binding.buttonSend.setOnClickListener {
            val enteredAmount = binding.editTextManat.text.toString().toDoubleOrNull()
            if (enteredAmount == null) {
                Toast.makeText(requireContext(), "Düzgün daxil edin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            transferViewModel.updateTotalAmount(enteredAmount)
            val newTotalAmount = transferViewModel.transferAmount.value ?: 0.0

            // Bildirim izni kontrolü
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                NotificationService(requireContext()).showBasicNotification()
            }

            findNavController().navigate(R.id.action_transferFragment_to_mainFragment)
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 101
    }
}