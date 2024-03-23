package com.leobank.presentation.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.leobank.R
import com.leobank.databinding.FragmentSmsAddBinding
import java.util.concurrent.TimeUnit


class smsAddFragment : Fragment() {
    private lateinit var binding: FragmentSmsAddBinding
    private lateinit var OTP: String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmsAddBinding.inflate(inflater, container, false)
        login()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        OTP = arguments?.getString("OTP").toString()
        resendToken = arguments?.getParcelable("resendToken")!!
        phoneNumber = arguments?.getString("phoneNumber")!!

        init()
        addTextChangeListener()
        resendOTPTvVisibility()

        binding.bttnIrali.setOnClickListener {
            val typedOTP = binding.editTextCode.text.toString()

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please Enter Correct OTP",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Enter OTP",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun resendOTPTvVisibility() {
        binding.editTextCode.setText("")

        Handler(Looper.myLooper()!!).postDelayed({

        }, 60000)
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {

                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            OTP = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Authenticate Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    sendToMain()
                } else {
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }
                }

            }
    }

    private fun sendToMain() {
        findNavController().navigate(R.id.action_smsAddFragment_to_emailAddFragment)
    }
    private fun login(){
        findNavController().navigate(R.id.action_smsAddFragment_to_pinCodeFragment2)

    }


    private fun addTextChangeListener() {
        binding.editTextCode.addTextChangedListener(EditTextWatcher(binding.editTextCode))
    }

    private fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when (view.id) {
                R.id.editTextCode -> if (text.length == 1) binding.editTextCode.requestFocus()
            }
        }
    }


}