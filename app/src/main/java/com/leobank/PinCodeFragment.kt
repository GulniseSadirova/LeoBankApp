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
    private lateinit var binding: FragmentPinCodeBinding
    private val enteredDigits = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.B0.setOnClickListener { appendDigit("0") }
        binding.B1.setOnClickListener { appendDigit("1") }
        binding.B2.setOnClickListener { appendDigit("2") }
        binding.B3.setOnClickListener { appendDigit("3") }
        binding.B4.setOnClickListener { appendDigit("4") }
        binding.B5.setOnClickListener { appendDigit("5") }
        binding.B6.setOnClickListener { appendDigit("6") }
        binding.B7.setOnClickListener { appendDigit("7") }
        binding.B8.setOnClickListener { appendDigit("8") }
        binding.B9.setOnClickListener { appendDigit("9") }

        // Silme (Remove) butonuna tıklama işlemi
        binding.BRemove.setOnClickListener {
            if (enteredDigits.isNotEmpty()) {
                enteredDigits.deleteCharAt(enteredDigits.length - 1)
                updatePinDisplay()
            }
        }

        // EditText dinleyicisi
        binding.editTextNumberPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Boş bırak
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Boş bırak
            }

            override fun afterTextChanged(s: Editable?) {
                // Boş bırak
            }
        })
    }

    // Rakam ekleme işlemi
    private fun appendDigit(digit: String) {
        if (enteredDigits.length < 4) {
            enteredDigits.append(digit)
            updatePinDisplay()

            // Eğer 4 hane girildiyse işlemi kontrol et
            if (enteredDigits.length == 4) {
                val correctPin = "1234" // Doğru PIN'i buraya yazın

                if (enteredDigits.toString() == correctPin) {
                    // Doğru PIN ise MainFragment'e geçiş yap
                    val navController = findNavController()
                    navController.navigate(R.id.action_pinCodeFragment_to_mainFragment2)
                } else {
                    // Yanlış PIN mesajı göster
                    Toast.makeText(requireContext(), "Yanlış PIN Kodu", Toast.LENGTH_SHORT).show()
                    enteredDigits.clear()
                    updatePinDisplay()
                }
            }
        }
    }

    // EditText'te girilen rakamları güncelle
    private fun updatePinDisplay() {
        binding.editTextNumberPassword.setText(enteredDigits.toString())
    }
}