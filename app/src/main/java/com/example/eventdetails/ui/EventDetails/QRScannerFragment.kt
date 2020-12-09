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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator

//Credits to ZXing Project :  QRCodeWriter
//Credits to kimkevin : CachePot
class QRScannerFragment : Fragment() {

    val eventID: String = CachePot.getInstance().pop(String::class.java)
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
                CachePot.getInstance().push(eventID)
                requireView().findNavController().navigate(R.id.navigation_home)
            } else {
                Toast.makeText(context, "Scanned : " + result.contents, Toast.LENGTH_LONG).show()
                val auth = Firebase.auth
                val user = auth.currentUser
                val userID = user?.uid
                var listLength : Int = 0
                val myRef = FirebaseDatabase.getInstance().reference.child("User").child("$userID").child("CompletedEvents")
                val myRef2 = FirebaseDatabase.getInstance().reference.child("User").child("$userID").child("VolunteeredEvents")
                val completedEventIDList: MutableList<String> = mutableListOf()
                myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) =
                        if (result.contents == eventID) {
                            myRef.push().setValue(eventID)
                            myRef2.addListenerForSingleValueEvent(object :ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (item in snapshot.children) {
                                        if (item.value.toString() == eventID)
                                        {
                                             myRef2.child(item.key.toString()).setValue(null)
                                        }
                                    }

                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })





                            CachePot.getInstance().push(eventID)
                            Toast.makeText(context, "Event Completed!", Toast.LENGTH_LONG).show()

                        }
                        else
                        {
                            Toast.makeText(context, "Wrong QRCode!", Toast.LENGTH_LONG).show()
                        }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })




                requireView().findNavController().navigate(R.id.navigation_home)
            }
        }
    }

}