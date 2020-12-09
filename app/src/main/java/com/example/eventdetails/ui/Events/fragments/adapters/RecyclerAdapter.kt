package com.android.example.eventactivity.fragments.adapters

import android.content.Context
import android.renderscript.Sampler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eventdetails.R
import com.example.eventdetails.ui.EventDetails.CompletedEventFragment
import com.example.eventdetails.ui.EventDetails.OrganiserEventsFragment
import com.example.eventdetails.ui.EventDetails.UserEventFragment
import com.example.eventdetails.ui.EventDetails.VolunteerEventsFragment
import com.example.eventdetails.ui.Firebase.EventRead
import com.github.kimkevin.cachepot.CachePot
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class RecyclerAdapter(val context: Context, val eventList: ArrayList<EventRead>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val itemTitle = itemView?.findViewById<TextView>(R.id.textView10)
        val itemDate = itemView?.findViewById<TextView>(R.id.textView6)
        val itemTime = itemView?.findViewById<TextView>(R.id.textView7)
        val itemLocation = itemView?.findViewById<TextView>(R.id.textView8)
        val itemContact = itemView?.findViewById<TextView>(R.id.textView9)

        lateinit var itemID: String

        init {
            itemView?.setOnClickListener {
                val position: Int = adapterPosition
                //Toast.makeText(itemView?.context, "You click on item # ${position + 1}, $itemID", Toast.LENGTH_SHORT).show()
                CachePot.getInstance().push(itemID);
                itemView?.findNavController().navigate(R.id.navigation_eventDetails)
            }
        }

        fun bind(event: EventRead, context: Context){
            itemTitle?.text = event.eventTitle
            itemDate?.text = event.eventDate
            itemTime?.text = event.eventTime.toString()
            itemLocation?.text = event.eventLocation
            itemContact?.text = event.eventContact
            itemID= event.eventID.toString()
            val auth = Firebase.auth
            val user = auth.currentUser?.uid
            val myRef = FirebaseDatabase.getInstance().reference.child("User").child("$user")


            val organiserRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("OrganisedEvents").orderByKey()
            val organiserEventIDList: MutableList<String> = mutableListOf()

            organiserRef.addListenerForSingleValueEvent(object : ValueEventListener {


                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children)
                    {
                        organiserEventIDList.add(item.value.toString())
                    }
                    if (itemID in organiserEventIDList)
                    {
                        val imageComplete = itemView?.findViewById<ImageView>(R.id.imageViewComplete)
                        val textViewComplete : TextView = itemView?.findViewById(R.id.textViewNew)
                        imageComplete.setImageResource(R.drawable.ic_baseline_event_note_24)
                        textViewComplete.text = "You are organising the event."
                    }
                    else
                    {
                        val volunteerRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("VolunteeredEvents").orderByKey()
                        val volunteerEventIDList: MutableList<String> = mutableListOf()
                        volunteerRef.addValueEventListener(object :ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (item in snapshot.children) {
                                    volunteerEventIDList.add(item.value.toString())
                                }

                                if (itemID in volunteerEventIDList)
                                {
                                    val imageComplete = itemView?.findViewById<ImageView>(R.id.imageViewComplete)
                                    val textViewComplete : TextView = itemView?.findViewById(R.id.textViewNew)
                                    imageComplete.setImageResource(R.drawable.ic_baseline_cancel_24)
                                    textViewComplete.text = "Not Completed!"
                                }
                                else
                                {
                                    val completedRef = FirebaseDatabase.getInstance().reference.child("User").child(user.toString()).child("CompletedEvents").orderByKey()
                                    val completedEventIDList: MutableList<String> = mutableListOf()
                                    completedRef.addValueEventListener(object :ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            for (item in snapshot.children) {
                                                completedEventIDList.add(item.value.toString())
                                            }

                                            if (itemID in completedEventIDList)
                                            {
                                                val imageComplete = itemView?.findViewById<ImageView>(R.id.imageViewComplete)
                                                val textViewComplete : TextView = itemView?.findViewById(R.id.textViewNew)
                                                imageComplete.setImageResource(R.drawable.ic_baseline_check_box_24)
                                                textViewComplete.text = "Completed!"
                                            }
                                            else
                                            {
                                                val imageComplete = itemView?.findViewById<ImageView>(R.id.imageViewComplete)
                                                val textViewComplete : TextView = itemView?.findViewById(R.id.textViewNew)
                                                imageComplete.setImageResource(R.drawable.ic_baseline_control_point_duplicate_24)
                                                textViewComplete.text = "Join Now!"
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            TODO("Not yet implemented")
                                        }
                                    })

                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_list, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bind(eventList[position], context)

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}

