package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.leobank.databinding.FragmentCreditsBinding


class CreditsFragment : Fragment() {
    private lateinit var binding: FragmentCreditsBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCreditsBinding.inflate(inflater,container,false)
        binding.bttnGetCreditLimit.setOnClickListener {
            Toast.makeText(requireContext(), "Siz hal-hazırda kredit limiti əldə edə bilməzsiniz", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }


}