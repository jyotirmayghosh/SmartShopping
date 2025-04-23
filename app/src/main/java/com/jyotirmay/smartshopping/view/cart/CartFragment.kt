package com.jyotirmay.smartshopping.view.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jyotirmay.smartshopping.R
import com.jyotirmay.smartshopping.databinding.FragmentCartBinding
import com.jyotirmay.smartshopping.view.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        mainViewModel.getProducts()

        mainViewModel.productLiveData.observe(requireActivity()) { list ->
            if (list.isNotEmpty()) {
                val mAdaptor = ProductAdaptor(list) { position ->
                    mainViewModel.removeProduct(list[position])
                }
                binding.apply {
                    btnCheckout.isEnabled = true
                    txtEmptyCart.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = mAdaptor
                    }
                }
            } else {
                binding.apply {
                    btnCheckout.isEnabled = false
                    txtEmptyCart.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            btnCheckout.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            }
            btnScanner.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_scanerFragment)
            }
        }
    }
}