package com.leobank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leobank.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
  private lateinit var binding: FragmentMainBinding
  private lateinit var adapter: Adapter
    private lateinit var viewModel: MainFragmentViewModel
    private var productList: ArrayList<Spending> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(inflater,container,false)
        click()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        setAdapter()
        observeProducts()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllProducts(requireContext())
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
                findNavController().navigate(R.id.action_mainFragment_to_singleItemFragment, bundle)
            }
        })

    }

    private fun click(){
        binding.myLeoCard.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_detailedCardFragment)
        }
    }


    private fun balanceIncrease(){
        binding.imageAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_balanceIncreaseFragment)
        }
    }
    private fun sendCard(){
        binding.imageForward.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sendToCardFragment)
        }
    }
    private fun payment(){
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