package com.jubging.jubging.ui.main

import com.jubging.jubging.R
import com.jubging.jubging.databinding.FragmentBannerBinding
import com.jubging.jubging.ui.base.BaseFragment


class Banner2Fragment(): BaseFragment<FragmentBannerBinding>(FragmentBannerBinding::inflate)
{

    override fun initAfterBinding() {
        
        
        binding.bannerImageIv.setImageResource(R.drawable.banner_1)
    }

}