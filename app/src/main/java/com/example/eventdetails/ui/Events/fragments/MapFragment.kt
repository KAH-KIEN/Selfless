package com.android.example.eventactivity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_map, container, false)
//        mapFragment = supportFragmentManager?.findFragmentById(R.id.mapFrag) as SupportMapFragment?
//        mapFragment?.getMapAsync(this)
        //mapFragment = supportFragmentManager.findFragmentById(R.id.mapFrag) as MapFragment
        //val mapFragment = activity?.supportFragmentManager?.findFragmentById(R.id.mapFrag) as SupportMapFragment?
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val kualaLumpurLatLng = LatLng(3.1390, 101.6869)
        val cherasLatLng = LatLng(3.1068, 101.7259)
        val setapakLatLng = LatLng(3.2791, 101.7410)
        val kuchingLatLng = LatLng(1.5535, 110.3593)
        val zoomLevel = 10f

        val kualaLumpur = googleMap.addMarker(
            MarkerOptions()
                .position(kualaLumpurLatLng)
                .title("Kuala Lumpur")
                .snippet("EventTitle: Date")
        )
        kualaLumpur.showInfoWindow()

        val cheras = googleMap.addMarker(
            MarkerOptions()
                .position(cherasLatLng)
                .title("Cheras")
                .snippet("EventTitle: Date")
        )
        cheras.showInfoWindow()

        val setapak = googleMap.addMarker(
            MarkerOptions()
                .position(setapakLatLng)
                .title("Setapak")
                .snippet("EventTitle: Date")
        )
        setapak.showInfoWindow()

        val kuching = googleMap.addMarker(
            MarkerOptions()
                .position(kuchingLatLng)
                .title("Kuching")
                .snippet("EventTitle: Date")
        )
        kuching.showInfoWindow()

//        val kuching = googleMap.addMarker(
//            MarkerOptions()
//                .position(kuchingLatLng)
//                .title("EventTitle")
//                .snippet("EventLocation: Date")
//        )
//        kuching.showInfoWindow()

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kualaLumpurLatLng, zoomLevel))

        googleMap.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(p0: Marker) {
                var event = p0.title
                //Intent
                Toast.makeText(requireActivity(), "You clicked on $event", Toast.LENGTH_SHORT).show()
                requireView().findNavController().navigate(R.id.navigation_eventDetails)
            }
            })

    }


}

