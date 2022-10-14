package com.jubging.jubging.ui.shop

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.jubging.jubging.R
import com.jubging.jubging.databinding.FragmentShopBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.jubging.jubging.ui.jubging.ShareBannerAdapter
import com.jubging.jubging.ui.main.Banner2Fragment
import com.jubging.jubging.ui.main.BannerFragment

class ShopFragment(): BaseFragment<FragmentShopBinding>(FragmentShopBinding::inflate) {
    override fun initAfterBinding() {

        val bannerAdapter = ShopBannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment())
        bannerAdapter.addFragment(Banner2Fragment())

        binding.shopBannerVp.adapter = bannerAdapter
        binding.shopBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }


}