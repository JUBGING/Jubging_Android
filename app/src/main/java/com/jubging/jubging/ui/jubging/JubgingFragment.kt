package com.jubging.jubging.ui.jubging

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.jubging.jubging.databinding.FragmentJubgingBinding
import java.io.IOException
import java.util.*


class JubgingFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{

    private var mContext: FragmentActivity? = null

    lateinit var binding: com.jubging.jubging.databinding.FragmentJubgingBinding
    private lateinit var mView: MapView
    private lateinit var mMap: GoogleMap
   // private lateinit var currentMarker : Marker

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var mCurrentLocation: Location? = null

    private val mDefaultLocation = LatLng(37.503371,126.957053)
    private val DEFAULT_ZOOM = 15f
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var mLocationPermissionGranted = false

    private val UPDATE_INTERVAL_MS = 1000 * 60 * 1 // 1분 단위 시간 갱신
    private val FASTEST_UPDATE_INTERVAL_MS = 1000 * 30




    override fun onAttach(context: Context) {
        mContext = activity as FragmentActivity
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentJubgingBinding.inflate(inflater,container,false)


        binding.jubgingPlayCl.setOnClickListener{
            val intent = Intent(activity, JipgaeNumActivity::class.java)
            startActivity(intent)
        }

        mView = binding.jubgingMap
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mContext?.let { MapsInitializer.initialize(it) }

        locationRequest = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // 정확도를 최우선적으로 고려
            .setInterval(UPDATE_INTERVAL_MS.toLong()) // 위치가 Update 되는 주기
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong()) // 위치 획득후 업데이트되는 주기

        val builder = LocationSettingsRequest.Builder()

        builder.addLocationRequest(locationRequest!!)

        // FusedLocationProviderClient 객체 생성
        fusedLocationProviderClient = mContext?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState!!)
        mView.onSaveInstanceState(outState)
    }


    private fun setDefaultLocation() {
        //if (currentMarker != null) currentMarker.remove()
        val markerOptions = MarkerOptions()
        val bitmapdraw = resources.getDrawable(com.jubging.jubging.R.drawable.trash_can_orange) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val customMarker = Bitmap.createScaledBitmap(b, 130, 130, false)

        markerOptions.position(mDefaultLocation)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(customMarker))

        mMap.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM)
        mMap.moveCamera(cameraUpdate)
    }


// 내위치 아이콘
    private fun updateLocationUI() {
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


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setDefaultLocation()
        getLocationPermission()
        updateLocationUI()
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)

    }


    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                val location = locationList[locationList.size - 1]
                //현재 위치에 마커 생성하고 이동
                //setCurrentLocation(location)
                mCurrentLocation = location
            }
        }
    }

//    fun setCurrentLocation(location: Location) {
//        //if (currentMarker != null) currentMarker.remove()
//        val currentLatLng = LatLng(location.latitude, location.longitude)
////        print(currentMarker)
//        val markerOptions = MarkerOptions()
//        markerOptions.position(currentLatLng)
//        markerOptions.draggable(true)
//        currentMarker = mMap.addMarker(markerOptions)!!
//        val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
//        mMap.moveCamera(cameraUpdate)
//    }


    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                mContext!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        mLocationPermissionGranted = false
//        when (requestCode) {
//            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
//                if (grantResults.size > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                ) {
//                    mLocationPermissionGranted = true
//                }
//            }
//        }
//        updateLocationUI()
//    }
//
//    fun checkLocationServicesStatus(): Boolean {
//        val locationManager =
//            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }


    override fun onMarkerClick(p0: Marker): Boolean {
        binding.jubgingInfoFl.visibility = View.VISIBLE
        return true
        //마커 위경도에 따라서 받은 값들 데이터에 들어가게끔
    }

    override fun onMapClick(p0: LatLng) {
        binding.jubgingInfoFl.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        mView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mView.onStop()
        if (fusedLocationProviderClient != null) {
            Log.d(TAG, "onStop : removeLocationUpdates");
            fusedLocationProviderClient!!.removeLocationUpdates(locationCallback);
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        mView.onResume()
        if (mLocationPermissionGranted) {
            Log.d(TAG, "onResume : requestLocationUpdates");
            locationRequest?.let { fusedLocationProviderClient!!.requestLocationUpdates(it, locationCallback, null) };
            if (mMap!=null)
                mMap.setMyLocationEnabled(true);
        }
    }

    override fun onPause() {
        super.onPause()
        mView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (fusedLocationProviderClient != null) {
            Log.d(TAG, "onDestroyView : removeLocationUpdates");
            fusedLocationProviderClient!!.removeLocationUpdates(locationCallback);
        }
    }

    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }




}