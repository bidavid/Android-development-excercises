package gg.bidavid.tvshowsapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm:FragmentManager): FragmentStatePagerAdapter(fm) {

    val fragmentList = ArrayList<DetailsFragment>()

    fun addFragment(fragment: DetailsFragment){
        fragmentList.add(fragment)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
         return fragmentList[position]
    }

    override fun getCount(): Int {
         return fragmentList.size
    }
}