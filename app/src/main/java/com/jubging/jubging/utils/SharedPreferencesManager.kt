package com.jubging.jubging.utils


import com.jubging.jubging.ApplicationClass.Companion.Authorization

import com.jubging.jubging.ApplicationClass.Companion.mSharedPreferences

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(Authorization, "Bearer $jwtToken")

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(Authorization, null)