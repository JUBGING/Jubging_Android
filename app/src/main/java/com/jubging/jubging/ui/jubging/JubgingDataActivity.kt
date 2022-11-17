package com.jubging.jubging.ui.jubging

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jubging.jubging.databinding.ActivityJubgingDataBinding


class JubgingDataActivity(): AppCompatActivity(),OnMapReadyCallback{
    private lateinit var binding: ActivityJubgingDataBinding
    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJubgingDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // map 연결
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(com.jubging.jubging.R.id.jubging_data_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.jubgindDataPlayFl.setOnClickListener{
            val intent = Intent(this, JipgaeNoticeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        binding.jubgingDataPlayIv.visibility = View.GONE
        binding.jubgingDataStopIv.visibility = View.VISIBLE

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val marker = LatLng(37.503371,126.957053)
//        mMap.addMarker(MarkerOptions().position(marker).title("마커 제목"))
//
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
    }



}