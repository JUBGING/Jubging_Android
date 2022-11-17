package com.jubging.jubging.ui.jubging

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.jubging.jubging.databinding.ActivityShareBinding
import com.jubging.jubging.ui.banner.Banner2Fragment
import com.jubging.jubging.ui.base.BaseActivity
import com.jubging.jubging.ui.banner.BannerFragment
import com.jubging.jubging.ui.main.MainActivity

class ShareActivity(): BaseActivity<ActivityShareBinding> (ActivityShareBinding::inflate){
    override fun initAfterBinding() {
        //배너 연결
        val bannerAdapter = ShareBannerAdapter(this)
        bannerAdapter.addFragment(BannerFragment())
        bannerAdapter.addFragment(Banner2Fragment())

        binding.shareBannerVp.adapter = bannerAdapter
        binding.shareBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        //클릭시
        binding.shareHomeBtnIv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.shareHomeIv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}