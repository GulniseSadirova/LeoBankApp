package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentDecreaseCardBinding
import com.leobank.presentation.viewmodel.DecreaseCardViewModel


class DecreaseCardFragment : Fragment() {
    private lateinit var binding: FragmentDecreaseCardBinding
    private val viewModel: DecreaseCardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDecreaseCardBinding.inflate(inflater, container, false)
        back()
        decrease()
        return binding.root
    }

    private fun back() {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_decreaseCardFragment_to_newCardAddFragment)
        }
    }

    private fun decrease() {
        binding.buttonSend.setOnClickListener {
            val enteredAmount = binding.editTextManat.text.toString().toDoubleOrNull()
            if (enteredAmount == null) {
                // Handle invalid input
                return@setOnClickListener
            }

            viewModel.updateCurrentAmount(enteredAmount)
            findNavController().navigate(R.id.action_decreaseCardFragment_to_mainFragment)
        }
    }
}