package com.leobank.presentation.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leobank.presentation.adapter.Adapter
import com.leobank.presentation.viewmodel.MainFragmentViewModel
import com.leobank.R
import com.leobank.domain.Spending
import com.leobank.presentation.viewmodel.TransferViewModel
import com.leobank.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var transferViewModel: TransferViewModel
    private var productList: ArrayList<Spending> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        click()
        transferViewModel = ViewModelProvider(requireActivity()).get(TransferViewModel::class.java)
        transferViewModel.initSharedPreferences(requireContext())




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        setAdapter()
        observeProducts()

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllProducts()
        }


        balanceIncrease()
        sendCard()
        payment()
        val transferViewModel: TransferViewModel by activityViewModels()
        transferViewModel.transferAmount.observe(viewLifecycleOwner) { amount ->
            viewModel.addToTotalAmount(amount)
        }
        viewModel.totalAmount.observe(viewLifecycleOwner) { total ->
            binding.txtMebleg.text = "$total"
        }
        adapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
            override fun onItemClick(item: Spending) {
                val bundle = Bundle()
                bundle.putInt("id", item.itemId)
                Log.e(TAG, "onItemClick: ${item.itemId}",)
                findNavController().navigate(R.id.action_mainFragment_to_singleItemFragment, bundle)
            }
        })
        binding.recylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.submitList(productList, newText.orEmpty())
                return true
            }
        })




    }

    private fun click() {
        binding.myLeoCard.setOnClickListener {
            binding.myLeoCard.animate().apply {
                rotationX(90f)
                duration = 500
                withEndAction {
                    findNavController().navigate(R.id.action_mainFragment_to_detailedCardFragment)
                }
            }
        }
    }


    private fun balanceIncrease() {
        binding.imageAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_balanceIncreaseFragment)
        }
    }

    private fun sendCard() {
        binding.imageForward.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sendToCardFragment)
        }
    }

    private fun payment() {
        binding.imageWallet.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_paymentFragment)
        }
    }

    private fun setAdapter() {
        adapter = Adapter(object : Adapter.OnItemClickListener {
            override fun onItemClick(spending: Spending) {
                val action = MainFragmentDirections.actionMainFragmentToSingleItemFragment()
                findNavController().navigate(action)
            }
        })
        binding.recylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recylerView.adapter = adapter
    }

    private fun observeProducts() {
        viewModel.productList.observe(viewLifecycleOwner) { productList ->
            adapter.submitList(productList)
            this.productList.clear()
            this.productList.addAll(productList)
        }
    }







}










