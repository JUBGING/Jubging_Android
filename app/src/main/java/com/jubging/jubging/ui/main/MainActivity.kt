package com.jubging.jubging.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.gun0912.tedpermission.PermissionListener
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivityMainBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.jubging.JubgingFragment
import com.jubging.jubging.ui.mypage.MypageFragment
import com.jubging.jubging.ui.shop.ShopFragment

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){
    private lateinit var navHostFragment: NavHostFragment

    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    companion object{
        var jubgingCountM = 0
    }



    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        binding.mainBottomNavigation.setupWithNavController(navController)

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        checkPermissionForLocation(this)
        //startLocationUpdates()

//        binding.mainBottomNavigation.setOnItemSelectedListener{
//            when(it.itemId){
//
//                R.id.homeFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_container, HomeFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//
//                R.id.jubgingFragment -> {
//         //           if(checkPermissionForLocation(this)){
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.nav_host_fragment_container, JubgingFragment())
//                            .commitAllowingStateLoss()
//                        //startLocationUpdates()
//                   // }
//                    return@setOnItemSelectedListener true
//                }
//                R.id.shopFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_container, ShopFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//
//                R.id.mypageFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_container, MypageFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//            }
//            false
//        }

    }

//    private fun startLocationUpdates(){
//        if(ActivityCompat.checkSelfPermission(
//                this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(
//                this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            return
//        }
//        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
//        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
////        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
//    }


    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSION_LOCATION)
                false
            }
        }else{
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //startLocationUpdates()
            }else{
                Toast.makeText(this, "위치 권한이 없어 해당 기능을 실행할 수 없습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }


}