package com.android.example.eventactivity.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eventdetails.R
import com.example.eventdetails.ui.Firebase.EventRead
import com.github.kimkevin.cachepot.CachePot


class RecyclerAdapter(val context: Context, val eventList: ArrayList<EventRead>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val itemTitle = itemView?.findViewById<TextView>(R.id.textView10)
        val itemDate = itemView?.findViewById<TextView>(R.id.textView6)
        val itemDuration = itemView?.findViewById<TextView>(R.id.textView7)
        val itemLocation = itemView?.findViewById<TextView>(R.id.textView8)
        val itemContact = itemView?.findViewById<TextView>(R.id.textView9)
        //val communicator = Communicator()

        lateinit var itemID: String

        init {
            itemView?.setOnClickListener {
                val position: Int = adapterPosition
                Toast.makeText(itemView?.context, "You click on item # ${position + 1}, $itemID", Toast.LENGTH_SHORT).show()
                CachePot.getInstance().push(itemID);
                //communicater.passID(itemID)
                itemView?.findNavController().navigate(R.id.navigation_eventDetails)
            }
        }

        fun bind(event: EventRead, context: Context){
            itemTitle?.text = event.eventTitle
            itemDate?.text = event.eventDate
            itemDuration?.text = event.eventDuration.toString()
            itemLocation?.text = event.eventLocation
            itemContact?.text = event.eventContact
            itemID = event.eventID.toString()

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

