package com.example.eventdetails.ui.EventDetails

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

class OrganiserEventsFragment : Fragment() {

    companion object {
        fun newInstance() = OrganiserEventsFragment()
    }

    private lateinit var viewModel: OrganiserEventsViewModel
    val eventID: String = CachePot.getInstance().pop(String::class.java)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.organiser_events_fragment, container, false)
        val buttonQR = root.findViewById<Button>(R.id.buttonQR)
        Log.i("QRButton", "$buttonQR")
        buttonQR.setOnClickListener {
            Log.i("QRButton2", "Clicked")
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.navigation_QRCode)
            /*val manager = parentFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.add(R.id.nav_view,QRCodeFragment())
            transaction.commit()*/

        }



        return root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrganiserEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }



}