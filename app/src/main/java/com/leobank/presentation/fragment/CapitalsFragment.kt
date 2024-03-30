package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leobank.R
import com.leobank.databinding.FragmentCapitalsBinding
import com.leobank.databinding.FragmentSendToCardBinding
import com.leobank.presentation.adapter.CapitalsAdapter
import com.leobank.presentation.adapter.SendCardAdapter
import com.leobank.presentation.viewmodel.CapitalsFragmentViewModel
import com.leobank.presentation.viewmodel.SendToCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CapitalsFragment : Fragment() {
    private lateinit var binding: FragmentCapitalsBinding
    private lateinit var adapter: CapitalsAdapter
    val viewModel: CapitalsFragmentViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCapitalsBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeCapitals()
        fetchCapitals()

    }

    private fun setAdapter() {
        // SendToCardViewModel'den gelen userList'i adapter'a aktararak adapter oluşturuyoruz.
        viewModel.capitalList.observe(viewLifecycleOwner) { capitalList ->
            adapter = CapitalsAdapter(capitalList)
            binding.recylerView.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recylerView.adapter = adapter
        }
    }

    private fun observeCapitals() {
        viewModel.capitalList.observe(viewLifecycleOwner) {

        }
    }

    private fun fetchCapitals() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAllCapitals(requireContext())
        }
    }




}