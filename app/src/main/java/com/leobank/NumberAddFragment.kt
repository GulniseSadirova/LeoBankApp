package com.leobank

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.leobank.databinding.FragmentNumberAddBinding
import java.util.concurrent.TimeUnit


class NumberAddFragment : Fragment() {

    private lateinit var binding: FragmentNumberAddBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var verificationId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberAddBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        // Callbacks tanımlaması
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Geçersiz istek
                } else if (e is FirebaseTooManyRequestsException) {
                    // Proje için SMS kotası aşıldı
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA doğrulaması null Activity ile yapıldı
                }
                // Hata durumuna göre işlemler yapılabilir
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent:$verificationId")
                this@NumberAddFragment.verificationId = verificationId
            }
        }

        binding.bttnIrali.setOnClickListener {
            val phoneNumber = binding.editTextNumber.text.toString().trim()
            startPhoneNumberVerification(phoneNumber)
        }

        return binding.root
    }

    private fun startPhoneNumberVerification(phone: String) {
        val options = callbacks?.let {
            PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(it)
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private fun verifyPhoneNumberWithCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                val phone = firebaseAuth.currentUser?.phoneNumber
                Toast.makeText(requireContext(), "Başarınız: $phone", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Doğrulama başarısız", Toast.LENGTH_SHORT).show()
            }
    }
}
