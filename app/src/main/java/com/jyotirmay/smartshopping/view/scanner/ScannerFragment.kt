package com.jyotirmay.smartshopping.view.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.jyotirmay.smartshopping.databinding.FragmentScanerBinding
import com.jyotirmay.smartshopping.view.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {

    private lateinit var binding: FragmentScanerBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var beepManager: BeepManager

    private val CAMERA_PERMISSION_CODE = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beepManager = BeepManager(requireActivity())
        initView()
    }

    private fun initView() {
        binding.apply {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startScanner()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
            btnClose.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanner()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun startScanner() {
        binding.barcodeScanner.apply {
            barcodeView.decoderFactory = DefaultDecoderFactory(BarcodeFormat.entries)
            initializeFromIntent(requireActivity().intent)

            decodeContinuous {
                pause()
                showAutoDismissAlert {
                    resume()
                }
                Log.d("SCAN_RESULT", it.text)
                mainViewModel.addProduct(it.text)
                beepManager.apply {
                    isBeepEnabled = true
                    playBeepSound()
                }
            }
        }
    }

    private fun showAutoDismissAlert(onDismiss: () -> Unit) {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Item added to cart")
            .setCancelable(false)
            .create()

        dialog.show()

        // Auto dismiss after 200ms
        Handler(Looper.getMainLooper()).postDelayed({
            if (dialog.isShowing) {
                dialog.dismiss()
                onDismiss()
            }
        }, 500)
    }


    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }

}