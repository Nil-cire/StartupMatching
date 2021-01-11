package com.eric.startupmatching.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ProfileViewpagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val titles: ArrayList<String> = ArrayList<String>()
    private val fragments: ArrayList<Fragment> = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size

    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment)
        titles.add(title)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}