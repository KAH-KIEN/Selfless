package com.example.eventdetails.ui.EventDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot

class VolunteerEventsFragment : Fragment() {

    companion object {
        fun newInstance() = VolunteerEventsFragment()
    }

    private lateinit var viewModel: VolunteerEventsViewModel
    val eventID: String = CachePot.getInstance().pop(String::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.volunteer_events_fragment, container, false)
        val buttonComplete = root.findViewById<Button>(R.id.buttonCompleteVolunteer)
        Log.i("ButtonComplete","$buttonComplete")
        buttonComplete.setOnClickListener {
            Log.i("OnClickButtonComplete","Clicked")
            CachePot.getInstance().push(eventID)
            requireView().findNavController().navigate(R.id.fragment_qrscanner)
            /*val manager = parentFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            transaction.add(R.id.nav_view,QRCodeFragment())
            transaction.commit()*/

        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VolunteerEventsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}