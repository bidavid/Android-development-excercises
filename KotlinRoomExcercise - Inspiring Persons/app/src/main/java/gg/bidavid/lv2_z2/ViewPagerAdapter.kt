package gg.bidavid.lv2_z2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import gg.bidavid.lv2_z2.fragments.edit_add_fragment.PersonEditFragment
import gg.bidavid.lv2_z2.fragments.list_fragment.PersonListFragment

class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private val fragmentList= listOf<Fragment>(PersonListFragment.newInstance(), PersonEditFragment.newInstance())
    private val titles = listOf<String>("Inspiring people", "Edit/Add")

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}