package com.jubging.jubging.ui.jubging

import androidx.viewpager2.widget.ViewPager2
import com.jubging.jubging.databinding.ActivityShareBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.banner.BannerFragment

class ShareActivity(): BaseActivity<ActivityShareBinding> (ActivityShareBinding::inflate){
    override fun initAfterBinding() {
        val bannerAdapter = ShareBannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment())

        binding.shareBannerVp.adapter = bannerAdapter
        binding.shareBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

}