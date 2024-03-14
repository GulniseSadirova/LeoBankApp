package com.leobank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.leobank.databinding.FragmentEmailAddBinding


class EmailAddFragment : Fragment() {
    private lateinit var binding: FragmentEmailAddBinding
    lateinit var firebaseAuth: FirebaseAuth




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEmailAddBinding.inflate(inflater,container,false)
        firebaseAuth = Firebase.auth

        bttnClick()
        return binding.root


    }
    private fun bttnClick() {
        binding.bttnIrali.setOnClickListener {
            firebaseAuth.signInWithEmailAndPassword(
                binding.editTextTextEmailAddress.text.toString(),
                binding.edtPassword.text.toString()
            ).addOnSuccessListener {
                Toast.makeText(requireContext(), "İstifadəçi uğurla daxil oldu", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_emailAddFragment_to_thansForSignUpFragment)
            }.addOnFailureListener { exception ->
                val message = exception.message
                Toast.makeText(requireContext(), "İstifadəçi uğurla daxil ola bilmədi: $message", Toast.LENGTH_LONG).show()
            }
        }
    }




}