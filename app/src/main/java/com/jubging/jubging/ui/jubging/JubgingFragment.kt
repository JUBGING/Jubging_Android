package com.jubging.jubging.ui.jubging

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
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
import com.jubging.jubging.data.remote.jubjubi.JubjubiService
import com.jubging.jubging.data.remote.jubjubi.JubjubiView
import com.jubging.jubging.databinding.FragmentJubgingBinding
import com.mummoom.md.data.remote.auth.JubjubiResponse


class JubgingFragment : Fragment(),JubjubiView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{

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

    private var jubjubiList: List<JubjubiResponse> = ArrayList()

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

        //새로고침 버튼 누르면
        binding.jubgingRefreshFl.setOnClickListener{
            getScreenLocation()
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


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDefaultLocation() {
        val markerOptions = MarkerOptions()
//        val bitmapdraw = resources.getDrawable(com.jubging.jubging.R.drawable.trash_can_orange) as BitmapDrawable
//        val b = bitmapdraw.bitmap
//        val customMarker = Bitmap.createScaledBitmap(b, 130, 130, false)

        markerOptions.position(mDefaultLocation)
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(customMarker))
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM)
//        mMap.addMarker(markerOptions)
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

//뷰설정해주고, 화면에서 좌표 받아서 좌표값 넘겨서 api 부름
    private fun getScreenLocation() {

        var leftTopPointLat = mMap.projection.visibleRegion.latLngBounds.northeast.latitude
        var leftTopPointLng = mMap.projection.visibleRegion.latLngBounds.northeast.longitude

        var rightBottomPointLat = mMap.projection.visibleRegion.latLngBounds.southwest.latitude
        var rightBottomPointLng = mMap.projection.visibleRegion.latLngBounds.southwest.longitude

        var screenLocation = "$leftTopPointLat,$rightBottomPointLng $rightBottomPointLat,$leftTopPointLng"


        val jubjubiService = JubjubiService()
        jubjubiService.setJubjubiView(this)
        jubjubiService.getJubjubiInfo(screenLocation)


        Log.d("좌표",screenLocation)
    }



    private fun setJubjubiInfo(jubjubiResponse: List<JubjubiResponse>) {
        //줍줍이 응답 받아서 리스트에 넣기
        jubjubiList = jubjubiResponse
        //응답 리스트 하나씩 마커 추가함수에 넣어서 마커 추가해주기
        for(element in jubjubiList){
            addMarker(element)
        }
        Log.d("줍줍리스폰스",jubjubiResponse.toString())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addMarker(newJubjubi:JubjubiResponse){
        Log.d("줍줍마커", newJubjubi.toString())
        val bitmapdraw = resources.getDrawable(com.jubging.jubging.R.drawable.trash_can_orange) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val customMarker = Bitmap.createScaledBitmap(b, 130, 130, false)

        val markerOptions = MarkerOptions()
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(customMarker))
        markerOptions.position(LatLng(newJubjubi.lat,newJubjubi.lng))
        //title에 줍줍이 이름
        markerOptions.title(newJubjubi.name)
        //alpha에 줍줍이 번호
        markerOptions.alpha(newJubjubi.jubjubi_id.toFloat())
        //snippet에 집게 개수
        markerOptions.snippet(newJubjubi.tongs_cnt.toString())
        mMap.addMarker(markerOptions)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setDefaultLocation()
        getLocationPermission()
        updateLocationUI()
        getScreenLocation()
        //mMap이 다른듯
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


    override fun onMarkerClick(marker: Marker): Boolean {
        binding.jubgingInfoFl.visibility = View.VISIBLE
        binding.jubgingTrashNameTv.text = marker.title
        binding.jubgingTrashNumTv.text = marker.alpha.toInt().toString()
        binding.jubgingJipgaeNumTv.text = marker.snippet
        return true
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

    override fun onJubjubiLoading() {
    }

    override fun onJubjubiSuccess(jubjubiResponse: List<JubjubiResponse>) {
        Log.d("줍줍리스폰스11",jubjubiResponse.toString())
        setJubjubiInfo(jubjubiResponse)
    }

    override fun onJubjubiFailure(errorCode: Int, message: String) {
        when(errorCode){
            4002 ->{
                Toast.makeText(context, "userPosition 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4011 ->{
                Toast.makeText(context, "비활성화된 사용자입니다.", Toast.LENGTH_SHORT).show()
                return
            }
            4017 ->{
                Toast.makeText(context, "회원탈퇴한 사용자입니다.", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }


}