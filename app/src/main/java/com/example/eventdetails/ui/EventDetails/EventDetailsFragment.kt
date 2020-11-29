package com.example.eventdetails.ui.EventDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class EventDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var eventDetailsViewModel: EventDetailsViewModel

    /*override fun onCreate(inflater: LayoutInflater,
                          container: ViewGroup?,
                          savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_eventdetails, container, false)
        val textViewTitle = root.findViewById(R.id.textViewTitle)
        val textViewDate = root.findViewById(R.id.textViewDate)
        val textViewTime = root.findViewById(R.id.textViewTime)
        val textViewLocation = root.findViewById(R.id.textViewLocation)
        val textViewPhoneNum = root.findViewById(R.id.textViewPhoneNum)
        val textViewDesc = root.findViewById(R.id.textViewDesc)

        val model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
        model!!.setMsgCommunicator(textViewTitle.text.toString(),textViewDate.text.toString(),textViewTime.text.toString(),textViewLocation.text.toString(),textViewPhoneNum.text.toString(),textViewDesc.text.toString())

    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Event Details Fragment","Created")

        setHasOptionsMenu(true);
        eventDetailsViewModel =
            ViewModelProvider(this).get(EventDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_eventdetails, container, false)
        val textViewTitle:TextView = root.findViewById(R.id.textViewTitle)
        val textViewDate:TextView = root.findViewById(R.id.textViewDate)
        val textViewTime:TextView = root.findViewById(R.id.textViewTime)
        val textViewLocation:TextView = root.findViewById(R.id.textViewLocation)
        val textViewPhoneNum:TextView = root.findViewById(R.id.textViewPhoneNum)
        val textViewDesc:TextView = root.findViewById(R.id.textViewDesc)

        val model= ViewModelProviders.of(requireActivity()).get(Communicator::class.java)
        val stringList: MutableList<String> = mutableListOf()
        val eventID: String = CachePot.getInstance().pop(String::class.java)
        Log.i("Lmao","$eventID")
        val myRef = FirebaseDatabase.getInstance().reference.child("Events").child(eventID)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                textViewTitle.text = snapshot.child("eventTitle").value.toString()
                textViewDate.text = snapshot.child("eventDate").value.toString()
                textViewTime.text = snapshot.child("eventTime").value.toString()
                textViewLocation.text = snapshot.child("eventLocation").value.toString()
                textViewPhoneNum.text = snapshot.child("eventContact").value.toString()
                textViewDesc.text = snapshot.child("eventDescription").value.toString()
                model.setMsgCommunicator(textViewTitle.text.toString(),textViewDate.text.toString(),textViewTime.text.toString(),textViewLocation.text.toString(),textViewPhoneNum.text.toString(),textViewDesc.text.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        Toast.makeText(context, "You click on item # $eventID", Toast.LENGTH_SHORT).show()
        Log.i("Event ID", "$eventID")



        model.title.observe(viewLifecycleOwner,
            { o -> textViewTitle.text = o!!.toString() })

        model.date.observe(viewLifecycleOwner,
            { o -> textViewDate.text = o!!.toString() })

        model.time.observe(viewLifecycleOwner,
            { o -> textViewTime.text = o!!.toString() })

        model.location.observe(viewLifecycleOwner,
            { o -> textViewLocation.text = o!!.toString() })

        model.phoneNum.observe(viewLifecycleOwner,
            { o -> textViewPhoneNum.text = o!!.toString() })

        model.desc.observe(viewLifecycleOwner,
            { o -> textViewDesc.text = o!!.toString() })

        val imageButtonEditText:ImageButton = root.findViewById(R.id.imageButtonEditText)

        model.passID("1234")

        imageButtonEditText.setOnClickListener {

            requireView().findNavController().navigate(R.id.navigation_editEvents)
            /*val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

            val editEventsFragment: Fragment? = parentFragmentManager.findFragmentById(R.id.navigation_editEvents)
            if (editEventsFragment != null) {
                fragmentTransaction.replace(R.id.nav_host_fragment,EditEventsFragment  )
            }
            fragmentTransaction.commit()*/
        }

        val buttonQR: Button = root.findViewById(R.id.buttonQR)

        buttonQR.setOnClickListener {
            Log.i("QR Button", "Clicked")
            requireView().findNavController().navigate(R.id.navigation_QRCode)

        }


        /*val textView: TextView = root.findViewById(R.id.text_dashboard)
        eventsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        inflater.inflate(R.menu.action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }




}
