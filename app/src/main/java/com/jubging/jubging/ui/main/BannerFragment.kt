package com.jubging.jubging.ui.main

import com.jubging.jubging.databinding.FragmentBannerBinding
import com.jubging.jubging.ui.base.BaseFragment


class BannerFragment(val imgRes : Int): BaseFragment<FragmentBannerBinding>(FragmentBannerBinding::inflate)
{
    override fun initAfterBinding() {
        binding.bannerImageIv.setImageResource(imgRes)
    }

}