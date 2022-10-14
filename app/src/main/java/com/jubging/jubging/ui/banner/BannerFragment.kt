package com.jubging.jubging.ui.banner

import com.jubging.jubging.R
import com.jubging.jubging.databinding.FragmentBannerBinding
import com.jubging.jubging.ui.base.BaseFragment


class BannerFragment(): BaseFragment<FragmentBannerBinding>(FragmentBannerBinding::inflate)
{

    override fun initAfterBinding() {
        
        
        binding.bannerImageIv.setImageResource(R.drawable.banner_0)
    }

}