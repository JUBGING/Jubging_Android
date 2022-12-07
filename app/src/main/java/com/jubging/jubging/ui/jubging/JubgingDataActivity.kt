package com.jubging.jubging.ui.jubging

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jubging.jubging.databinding.ActivityJubgingDataBinding


class JubgingDataActivity(): AppCompatActivity(),OnMapReadyCallback{
    private lateinit var binding: ActivityJubgingDataBinding
    private lateinit var mMap: GoogleMap

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var mCurrentLocation: Location? = null
    private val DEFAULT_ZOOM = 15
    private var mLocationPermissionGranted = false
    private val UPDATE_INTERVAL_MS = 1000 * 60 * 15
    private val FASTEST_UPDATE_INTERVAL_MS = 1000 * 30
    private val REQUEST_PERMISSION_LOCATION = 10
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    private var distance =0.0
    private var time = 0
    private var walk = 0
    private var kcal = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJubgingDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS.toLong())
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())


        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest!!)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // map 연결
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(com.jubging.jubging.R.id.jubging_data_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.jubgingDataWalk.text = walk.toString()
        binding.jubgingDataDistance.text = distance.toString()
        binding.jubgingDataTime.text = time.toString()
        binding.jubgingDataKcal.text = kcal.toString()


        binding.jubgindDataPlayFl.setOnClickListener{
            val intent = Intent(this, JipgaeNoticeActivity::class.java)
            intent.putExtra("walk",walk)
            intent.putExtra("distance",distance)
            intent.putExtra("time",time)
            intent.putExtra("kcal",kcal)
            this.intent.getIntExtra("jubjubi_id",0).let { intent.putExtra("jubjubi_id", it)}
            this.intent.getIntExtra("tongs_id",0).let { intent.putExtra("tongs_id", it)}
            startActivity(intent)
        }

    }




    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocationPermission()
        updateLocationUI()

        val cau = LatLng(37.503371,126.957053)

        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cau))

    }

    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                mCurrentLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message.toString())
        }
    }

    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]
                setCurrentLocation(location)
                mCurrentLocation = location
            }
        }
    }

    fun setCurrentLocation(location: Location) {
        val currentLatLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
        mMap.moveCamera(cameraUpdate)
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSION_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mLocationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    override fun onStart() {
        super.onStart()
        if (mLocationPermissionGranted) {
            Log.d(TAG, "onStart : requestLocationUpdates")
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            mFusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest!!,
                locationCallback,
                null
            )
            if (mMap != null) mMap.isMyLocationEnabled = true
        }
        binding.jubgingDataPlayIv.visibility = View.GONE
        binding.jubgingDataStopIv.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onStop : removeLocationUpdates")
            mFusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mFusedLocationProviderClient != null) {
            Log.d(TAG, "onDestroy : removeLocationUpdates")
            mFusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
        }
    }

}