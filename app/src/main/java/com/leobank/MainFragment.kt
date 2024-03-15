package com.leobank

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.leobank.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: Adapter
    private lateinit var viewModel: MainFragmentViewModel
    private var productList: ArrayList<Spending> = ArrayList()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        click()
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // Kayıtlı toplam miktarı al ve txtMebleg üzerine yaz
        val totalAmount = sharedPreferences.getFloat("totalAmount", 0f)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.setTotalAmount(totalAmount.toDouble())
        binding.txtMebleg.text = totalAmount.toString()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        setAdapter()
        observeProducts()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllProducts(requireContext())
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.addToTotalAmount(130.0)
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
            saveTotalAmount(total)
        }

    }

    private fun click() {
        binding.myLeoCard.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_detailedCardFragment)
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
        adapter = Adapter()
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
    private fun saveTotalAmount(total: Double) {
        // SharedPreferences'e toplam miktarı kaydet
        sharedPreferences.edit().putFloat("totalAmount", total.toFloat()).apply()
    }










}