package com.example.eventdetails.ui.EventDetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.zxing.integration.android.IntentIntegrator


class QRScannerFragment : Fragment() {


    companion object {
        fun newInstance() = QRScannerFragment()
    }

    private lateinit var viewModel: QRScannerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val integrator: IntentIntegrator = IntentIntegrator.forSupportFragment(this)


        integrator.setOrientationLocked(true)
        integrator.setPrompt("Scan QR code")
        integrator.setBeepEnabled(false)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)


        integrator.initiateScan()
        
        return inflater.inflate(R.layout.fragment_qrscanner, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QRScannerViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Scanned : " + result.contents, Toast.LENGTH_LONG).show()
                requireView().findNavController().navigate(R.id.navigation_home)
            }
        }
    }

}