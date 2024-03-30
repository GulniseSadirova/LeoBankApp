package com.leobank.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leobank.R
import com.leobank.databinding.FragmentSendToCardBinding
import com.leobank.domain.Edv
import com.leobank.presentation.adapter.EdvAdapter
import com.leobank.presentation.adapter.SendCardAdapter
import com.leobank.presentation.viewmodel.EdvFragmentViewModel
import com.leobank.presentation.viewmodel.SendToCardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SendToCardFragment : Fragment() {
    private lateinit var binding: FragmentSendToCardBinding
    private lateinit var adapter: SendCardAdapter
    val viewModel: SendToCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendToCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeUsers()
        fetchUsers()
        setClickListeners()
    }

    private fun setAdapter() {

        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            adapter = SendCardAdapter(userList)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
        }
    }

    private fun observeUsers() {
        viewModel.userList.observe(viewLifecycleOwner) { userList ->

        }
    }

    private fun fetchUsers() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAllUsers(requireContext())
        }
    }

    private fun setClickListeners() {
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_sendToCardFragment_to_mainFragment)
        }

        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.action_sendToCardFragment_to_newCardAddFragment)
        }
    }
}