package com.android.example.eventactivity.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import com.example.eventdetails.R
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    var compactCalendar: CompactCalendarView? = null
    private val dateFormatMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater!!.inflate(R.layout.fragment_calendar, container, false)
        val textView1: TextView = view.findViewById(R.id.textDate)
        textView1.setText(dateFormatMonth)

        compactCalendar = view.findViewById<View>(R.id.compactcalendar_view) as CompactCalendarView
        compactCalendar!!.setUseThreeLetterAbbreviation(true)

        val ev1 = Event(Color.RED, 1606492800000L, "Teachers' Professional Day")
        compactCalendar!!.addEvent(ev1)
        compactCalendar!!.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val context = getActivity()?.getApplicationContext()
                if (dateClicked.toString().compareTo("Sat Nov 28 00:00:00 GMT+08:00 2020") == 0) {
                    Toast.makeText(
                            context,
                            "Teachers' Professional Day",
                            Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                            context,
                            "No Events Planned for that day",
                            Toast.LENGTH_SHORT
                    )
                            .show()
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
//                val dateFormatScroll = SimpleDateFormat("MMM, yyyy", Locale.getDefault())
//                val dateF: Date = dateFormatScroll.parse(firstDayOfNewMonth.toString())
                //textView1.text = dateF.toString()
                val inputFormat: DateFormat = SimpleDateFormat("EE MMM dd HH:mm:ss zz yyy")
                val d: Date = inputFormat.parse(firstDayOfNewMonth.toString())
                val outputFormat: DateFormat = SimpleDateFormat("MMMM yyyy")
                //System.out.println(outputFormat.format(d))
                Log.d("TAG", "Month was scrolled to: " + outputFormat.format(d))
                textView1.setText(outputFormat.format(d))
            }
        })


        return view
    }



}

//        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
//        val myDate: TextView = view.findViewById(R.id.myDate)



//        calendarView.setOnDateChangeListener {calenderView, i, i2, i3 ->
//            val pickData: String = "$i3/$i2/$i"
//            myDate.setText(pickData)
//        }