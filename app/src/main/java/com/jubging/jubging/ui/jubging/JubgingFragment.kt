package com.jubging.jubging.ui.jubging

import android.R
import android.os.Bundle
import androidx.lifecycle.Transformations.map
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.jubging.jubging.databinding.FragmentJubgingBinding
import com.jubging.jubging.ui.base.BaseFragment


class JubgingFragment(): BaseFragment<FragmentJubgingBinding>(FragmentJubgingBinding::inflate), OnMapReadyCallback {

    private var map: GoogleMap? = null

    override fun initAfterBinding() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }


    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }
}