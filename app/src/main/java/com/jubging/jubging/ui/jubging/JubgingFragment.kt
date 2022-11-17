package com.jubging.jubging.ui.jubging

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.jubging.jubging.databinding.FragmentJubgingBinding



class JubgingFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener{

    lateinit var binding: com.jubging.jubging.databinding.FragmentJubgingBinding
    private lateinit var mView: MapView
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJubgingBinding.inflate(inflater,container,false)

        binding.jubgingPlayCl.setOnClickListener{
            val intent = Intent(activity, BluetoothActivity::class.java)
            startActivity(intent)
        }

        mView = binding.jubgingMap
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)
        return binding.root

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val markerOptions = MarkerOptions()
        val cau = LatLng(37.503371,126.957053)

        val bitmapdraw = resources.getDrawable(com.jubging.jubging.R.drawable.trash_can) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 130, 130, false)

        markerOptions.title("중앙대").position(cau).icon(BitmapDescriptorFactory.fromBitmap(smallMarker))

        mMap.addMarker(markerOptions)

        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cau))

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)

    }

    override fun onMarkerClick(p0: Marker): Boolean {
        binding.jubgingInfoFl.visibility = View.VISIBLE
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
    }

    override fun onResume() {
        super.onResume()
        mView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }

    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }




}