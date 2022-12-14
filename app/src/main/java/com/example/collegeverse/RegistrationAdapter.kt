package com.example.clgshare

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.coroutines.InternalCoroutinesApi


class RegistrationAdapter(fm: FragmentManager?, registration: Registration, tabCount: Int) : FragmentPagerAdapter(
    fm!!
) {

    private var context: Context? = registration
    var totalTabs = tabCount



    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SigninFragment()
            }
            1 -> {
                SignupFragment()
            }
            else -> {SigninFragment()}
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}