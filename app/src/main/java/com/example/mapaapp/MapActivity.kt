package com.example.mapaapp

import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

//Julio Noboa 2022-1015
class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var name: String
    private lateinit var surname: String
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        name = intent.getStringExtra("name").orEmpty()
        surname = intent.getStringExtra("surname").orEmpty()
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val map = googleMap
            val name = intent.getStringExtra("name") ?: "Unknown"
            val surname = intent.getStringExtra("surname") ?: "Unknown"
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)

            val location = LatLng(latitude, longitude)
            val addressInfo = getAddressFromCoordinates(latitude, longitude)

            val marker = map.addMarker(
                MarkerOptions().position(location)
                    .title("$name $surname")
                    .snippet(addressInfo)
            )

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

            map.setOnMarkerClickListener { clickedMarker ->
                if (clickedMarker == marker) {
                    marker.showInfoWindow()
                }
                true
            }
        }

    private fun getAddressFromCoordinates(latitude: Double, longitude: Double): String {
        return try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address = addresses[0]
                val city = address.locality ?: "Unknown city"
                val country = address.countryName ?: "Unknown country"
                "$city, $country"
            } else {
                "No address found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error retrieving address"
        }
    }
}