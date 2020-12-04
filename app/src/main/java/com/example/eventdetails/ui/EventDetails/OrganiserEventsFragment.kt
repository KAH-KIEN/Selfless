package com.example.eventdetails.ui.EventDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//Credits to kimkevin : CachePot
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
        val buttonComplete = root.findViewById<Button>(R.id.buttonCompleteOrganiser)
        Log.i("QRButton", "$buttonQR")
        val ref = FirebaseDatabase.getInstance().reference.child("Events").child("$eventID")
        var whatsAppLink :String
        ref.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                whatsAppLink = snapshot.child("eventWhatsapp").value.toString()
                val textViewWhatsApp:TextView= root.findViewById(R.id.textViewWhatsAppOrganiser)
                textViewWhatsApp.text = whatsAppLink
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        buttonQR.setOnClickListener {
            Log.i("QRButton2", "Clicked")
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.navigation_QRCode)

        }

        buttonComplete.setOnClickListener {
            CachePot.getInstance().push(eventID)
            Toast.makeText(context, "Event Completed!", Toast.LENGTH_LONG).show()
            requireView().findNavController().navigate(R.id.navigation_home)
            FirebaseDatabase.getInstance().reference.child("Events").child("$eventID").removeValue()

        }

        val imageButtonEditText = root.findViewById<ImageButton>(R.id.imageButtonEditText)
        imageButtonEditText.setOnClickListener {
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.navigation_editEvents)
            val fragmentTransaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()

            val editEventsFragment: Fragment? =
                parentFragmentManager.findFragmentById(R.id.navigation_editEvents)
            if (editEventsFragment != null) {
                fragmentTransaction.replace(R.id.nav_host_fragment, EditEventsFragment())
            }
            fragmentTransaction.commit()
        }


        val textWhatsApp = root.findViewById(R.id.textViewWhatsAppOrganiser) as TextView
        textWhatsApp.movementMethod = LinkMovementMethod.getInstance()


        return root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrganiserEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }



}