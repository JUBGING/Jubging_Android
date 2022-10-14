package com.jubging.jubging.ui.shop

import androidx.viewpager2.widget.ViewPager2
import com.jubging.jubging.databinding.FragmentShopBinding
import com.jubging.jubging.ui.base.BaseFragment
import com.jubging.jubging.ui.banner.Banner2Fragment
import com.jubging.jubging.ui.banner.BannerFragment

class ShopFragment(): BaseFragment<FragmentShopBinding>(FragmentShopBinding::inflate) {
    override fun initAfterBinding() {

        val bannerAdapter = ShopBannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment())
        bannerAdapter.addFragment(Banner2Fragment())

        binding.shopBannerVp.adapter = bannerAdapter
        binding.shopBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }


}