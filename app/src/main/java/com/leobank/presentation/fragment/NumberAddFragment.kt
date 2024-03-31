package com.leobank.presentation.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.leobank.MainActivity
import com.leobank.R
import com.leobank.databinding.FragmentNumberAddBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class NumberAddFragment : Fragment() {

    private lateinit var binding: FragmentNumberAddBinding
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var verificationId: String = ""
    private lateinit var number:String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNumberAddBinding.inflate(inflater, container, false)
        init()
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding.bttnIrali.setOnClickListener {
            number = binding.editTextNumber.text.trim().toString()
            if (number.isNotEmpty()) {
                if (number.length == 10) {
                    number = "+90$number"
                    val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(callbacks!!)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                } else {
                    Toast.makeText(requireContext(), "Please Enter correct Number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please Enter Number", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        firebaseAuth = FirebaseAuth.getInstance()

        if (isUserLoggedIn()) {
            sendToMain()
        }
    }

    private fun init() {
//        firebaseAuth = FirebaseAuth.getInstance()
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

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
                findNavController().navigate(
                    R.id.action_numberAddFragment_to_smsAddFragment,
                    bundleOf(
                        "OTP" to verificationId,
                        "resendToken" to token,
                        "phoneNumber" to number
                    )
                )
            }

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    saveUserLoggedInFlag(true)
                    val firebaseUser = firebaseAuth.currentUser
                    val userId = firebaseUser?.uid ?: ""
                    val userName = firebaseUser?.displayName ?: ""
                    val userBalance = 0.0
                    saveUserData(userId, userName, userBalance)
                    Toast.makeText(requireContext(), "Authenticate Successfully", Toast.LENGTH_SHORT).show()
                    sendToMain()

                } else {
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }

                }
            }
    }
    private fun saveUserData(userId: String, userName: String, userBalance: Double) {
        val sharedPrefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("userId", userId)
        editor.putString("userName", userName)
        editor.putFloat("userBalance", userBalance.toFloat())
        editor.apply()
    }


    private fun sendToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("userLoggedIn", false)
    }
    private fun saveUserLoggedInFlag(loggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("userLoggedIn", loggedIn).apply()
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            sendToMain()
        }
    }
}