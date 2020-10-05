package gg.bidavid.tvshowsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import gg.bidavid.tvshowsapp.model.SearchResult
import kotlinx.android.synthetic.main.activity_tv_show_details.*

class TvShowDetailsActivity : AppCompatActivity() {

    lateinit var searchResultList: ArrayList<SearchResult>
    var startingPosition: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_details)
        retrieveData()
        setUpViewPager()
        TVshowsViewPager.setCurrentItem(startingPosition!!, true)

    }

    private fun setUpViewPager() {
        val adapter= ViewPagerAdapter(supportFragmentManager)
        for(i in 0 until searchResultList.size){
            adapter.addFragment(DetailsFragment.getInstance(searchResultList[i]))
        }
        TVshowsViewPager.adapter = adapter
    }

    private fun retrieveData() {
        val intent = this.intent
        if(intent.hasExtra(POSITION_KEY)&& intent.hasExtra(RESPONSE_LIST_KEY)){
            this.startingPosition= intent.getIntExtra(POSITION_KEY,0)
            this.searchResultList= intent.getSerializableExtra(RESPONSE_LIST_KEY) as ArrayList<SearchResult>
        }
    }
}
