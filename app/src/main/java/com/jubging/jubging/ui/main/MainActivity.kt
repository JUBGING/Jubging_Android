package com.jubging.jubging.ui.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivityMainBinding
import com.jubging.jubging.ui.base.BaseActivity

class MainActivity: BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var navHostFragment: NavHostFragment
    private var index: Int = 1
    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()

        binding.mainBottomNavigation.setupWithNavController(navController)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if(intent.hasExtra("index")){
//            index = intent.getIntExtra("index",1)
//            //changeFragment(index)
//            initNavigation(index)
//        }
//    }
//
//    private fun initNavigation(index:Int) {
//
//        when (index){
//            1 -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.nav_host_fragment_container, JubgingDataFragment())
//                    .commitAllowingStateLoss()
//            }
//        }
//        binding.mainBottomNavigation.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.homeFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_container, HomeFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//
//                R.id.jubgingFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.nav_host_fragment_container, JubgingDataFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
//
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
//
//            }
//            false
//        }
//    }




}