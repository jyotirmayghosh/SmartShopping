package com.jyotirmay.smartshopping.view.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jyotirmay.smartshopping.R
import com.jyotirmay.smartshopping.databinding.FragmentCheckoutBinding
import com.jyotirmay.smartshopping.view.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        mainViewModel.getProducts()
        mainViewModel.productLiveData.observe(requireActivity()) { list ->
            var totalPrice = 0.0
            list.forEach { productEntity ->
                totalPrice += productEntity.productPrice
            }

            binding.textSubtotalValue.text = "$totalPrice"
            val tax = totalPrice * 0.18
            binding.textTaxValue.text = "$tax"
            binding.textTotalValue.text = "${totalPrice + tax}"
        }
    }

    private fun initView() {
        binding.apply {
            imageBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_checkoutFragment_to_scanerFragment)
            }
            btnConfirm.setOnClickListener {
                showConfirmDialog {
                    mainViewModel.clearAllProducts()
                    Toast.makeText(requireContext(), "Order placed successfully", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun showConfirmDialog(onConfirm: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Order")
            .setMessage("Are you sure you want to proceed?")
            .setPositiveButton("Yes") { dialog, _ ->
                onConfirm()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }


}