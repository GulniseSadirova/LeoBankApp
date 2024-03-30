package com.leobank.presentation.fragment



import android.content.ContentValues.TAG

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leobank.domain.Edv
import com.leobank.presentation.adapter.EdvAdapter
import com.leobank.presentation.viewmodel.EdvFragmentViewModel
import com.leobank.R

import com.leobank.databinding.FragmentEdvBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EdvFragment : Fragment() {
    private lateinit var binding: FragmentEdvBinding
    private lateinit var adapter: EdvAdapter
    val  viewModel: EdvFragmentViewModel by viewModels()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEdvBinding.inflate(inflater, container, false)
        binding.imageqR.setOnClickListener {
            findNavController().navigate(R.id.action_edvFragment_to_scanFragment)
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeProducts()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllProducts(requireContext())
        }
        adapter.setOnItemClickListener(object : EdvAdapter.OnItemClickListener {
            override fun onItemClick(item: Edv) {
                val bundle = Bundle()
                bundle.putInt("id", item.itemId)
                Log.e(TAG, "onItemClick: ${item.itemId}", )
                findNavController().navigate(R.id.action_edvFragment_to_singleEdvFragment, bundle)
            }
        })
    }

    private fun setAdapter() {
        adapter = EdvAdapter(object : EdvAdapter.OnItemClickListener {
            override fun onItemClick(item: Edv) {
                val action = EdvFragmentDirections.actionEdvFragmentToSingleEdvFragment()
                findNavController().navigate(action)
            }
        })
        binding.recylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerView.adapter = adapter
    }

    private fun observeProducts() {
        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            adapter.submitList(productList)
        }
    }
}