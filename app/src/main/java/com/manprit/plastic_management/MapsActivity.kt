package com.manprit.plastic_management

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.manprit.plastic_management.databinding.ActivityMapsBinding
import com.manprit.plastic_management.loginSignup.loginactivity

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissioncode = 101
    lateinit var latitude : String
    lateinit var longitude : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val lat = intent.getStringExtra("latitide")
        val long = intent.getStringExtra("longitude")

//        println("----------------------------------")
//        println(lat)
//        println(long)

        if (lat.isNullOrBlank()||long.isNullOrBlank())
            getCurrentLocationUser()
        else{
            latitude=lat
            longitude=long
            getCurrentLocationUser()
//
//            println(latitude)
//            println(longitude)
//
//            println(latitude.toDouble())
//            println(longitude.toDouble())
        }

    }

    private fun getCurrentLocationUser() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if(LocationEnabled()) {
                val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener {

                    if (it != null) {
                        currentLocation = it
                        val latitude = currentLocation.latitude.toString()
                        val longitude = currentLocation.longitude.toString()
                        Toast.makeText(
                            applicationContext,
                            this.latitude + "" + this.longitude,
                            Toast.LENGTH_LONG
                        )
                            .show()

                        val mapFragment = supportFragmentManager
                            .findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(this)
                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "Map Error! ", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(applicationContext, "Location Error!\nPlease Turn on Location ! ", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        }else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),
                permissioncode
            )
            return
        }


    }

    fun LocationEnabled(): Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissioncode -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocationUser()
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        if(this.latitude.isNullOrBlank() || this.latitude.isNullOrBlank()){
            val dialog = AlertDialog.Builder(this@MapsActivity)
            dialog.setTitle("Logout")
            dialog.setMessage("Are you sure you want to logout ? ")
            dialog.setPositiveButton("YES"){ text , listener ->

            }
            dialog.setNegativeButton("NO"){text , listener ->  }
            dialog.create()
            dialog.show()
        }else{

            var latitude = this.latitude.toDouble()
            var longitude = this.longitude.toDouble()

            println("---------------------------------------")
            println(latitude)
            println(longitude)

            val latLng = LatLng(latitude, longitude)
            val markerOptions = MarkerOptions().position(latLng).title("Current Location")

            googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f))
            googleMap?.addMarker(markerOptions)
        }

    }
}