package com.android.example.eventactivity.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.eventdetails.R
import com.github.kimkevin.cachepot.CachePot
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_map, container, false)
        // Ask for permission
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fetchLocation()
        return view
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }

        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
//                Toast.makeText(
//                    requireActivity(), currentLocation.latitude.toString() + "" +
//                            currentLocation.longitude, Toast.LENGTH_SHORT
//                ).show()
                val mapFragment = childFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment?
                mapFragment?.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                //When permission are granted
                //Call method
                fetchLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val kualaLumpurLatLng = LatLng(3.1390, 101.6869)
        val cherasLatLng = LatLng(3.1068, 101.7259)
        val setapakLatLng = LatLng(3.2791, 101.7410)
        val kuchingLatLng = LatLng(1.5535, 110.3593)
        val melakaLatLng = LatLng(2.1896, 102.2501)
        val zoomLevel = 15f

        val kualaLumpur = googleMap.addMarker(
            MarkerOptions()
                .position(kualaLumpurLatLng)
                .title("Yellow House KL")
                .snippet("Kuala Lumpur, 12/12/2020")
        )
        kualaLumpur.showInfoWindow()

        val cheras = googleMap.addMarker(
            MarkerOptions()
                .position(cherasLatLng)
                .title("Kechara Soup Kitchen")
                .snippet("Cheras, 18/12/2020")
        )
        cheras.showInfoWindow()

        val setapak = googleMap.addMarker(
            MarkerOptions()
                .position(setapakLatLng)
                .title("ZOO INVESTIGATORS")
                .snippet("Setapak, 16/12/2020")
        )
        setapak.showInfoWindow()

        val kuching = googleMap.addMarker(
            MarkerOptions()
                .position(kuchingLatLng)
                .title("Sea Turtle Conservation Volunteering")
                .snippet("Kuching, 14/12/2020")
        )
        kuching.showInfoWindow()

        val melaka = googleMap.addMarker(
            MarkerOptions()
                .position(melakaLatLng)
                .title("Lean In -马六甲老年关怀项目")
                .snippet("Melaka, 27/12/2020")
        )
        melaka.showInfoWindow()

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        val currentLocation = googleMap.addMarker(markerOptions)

        googleMap.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(p0: Marker) {
                var event: String = p0.title
                Toast.makeText(requireActivity(), "You clicked on $event", Toast.LENGTH_SHORT).show()

                when(event){
                    "Yellow House KL" ->  CachePot.getInstance().push("-MN8gQlral-Z3lf2XXQO");
                    "Kechara Soup Kitchen" ->  CachePot.getInstance().push("-MNHkE0Sur6crMJpymW8");
                    "ZOO INVESTIGATORS" ->  CachePot.getInstance().push("-MNC96_5LJaNGa7fNbKT");
                    "Sea Turtle Conservation Volunteering" ->  CachePot.getInstance().push("-MNCRPjpyNZDjfwmoVXh");
                    "Lean In -马六甲老年关怀项目" ->  CachePot.getInstance().push("-MNHz1qxNWX8rxjvTfto");
                    else -> throw Exception("Error")
                }
                requireView().findNavController().navigate(R.id.navigation_eventDetails)
            }
            })

    }

}

