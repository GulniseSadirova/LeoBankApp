package com.leobank

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.leobank.databinding.FragmentPinCodeBinding


class PinCodeFragment : Fragment() {
    private lateinit var binding:FragmentPinCodeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPinCodeBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextNumberPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Boş bırak
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Boş bırak
            }

            override fun afterTextChanged(s: Editable?) {
                val enteredPin = s.toString()

                // Girilen PIN 4 haneli ise otomatik olarak MainFragment'e geçiş yap
                if (enteredPin.length == 4) {
                    val correctPin = "1234" // Doğru PIN'i buraya yazın

                    if (enteredPin == correctPin) {
                        // Doğru PIN ise MainFragment'e geçiş yap
                        val navController = findNavController()
                        navController.navigate(R.id.action_pinCodeFragment_to_mainFragment2)
                    } else {
                        // Yanlış PIN mesajı göster
                        Toast.makeText(requireContext(), "Yanlış PIN Kodu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }



}