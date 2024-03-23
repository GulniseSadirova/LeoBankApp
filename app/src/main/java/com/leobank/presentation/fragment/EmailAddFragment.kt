package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.leobank.R
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
                findNavController().navigate(R.id.action_emailAddFragment_to_thansForSignUpFragment)

        }
    }




}