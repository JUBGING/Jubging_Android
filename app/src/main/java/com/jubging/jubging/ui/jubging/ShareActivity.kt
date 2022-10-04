package com.jubging.jubging.ui.jubging

import androidx.viewpager2.widget.ViewPager2
import com.jubging.jubging.R
import com.jubging.jubging.databinding.ActivityShareBinding
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.main.BannerFragment
import com.jubging.jubging.ui.main.ShareBannerAdapter

class ShareActivity(): BaseActivity<ActivityShareBinding> (ActivityShareBinding::inflate){
    override fun initAfterBinding() {
        val bannerAdapter = ShareBannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.banner_1))
        bannerAdapter.addFragment(BannerFragment(R.drawable.banner_2))

        binding.shareBannerVp.adapter = bannerAdapter
        binding.shareBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

}