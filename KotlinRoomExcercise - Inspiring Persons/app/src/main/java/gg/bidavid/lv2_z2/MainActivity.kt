package gg.bidavid.lv2_z2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import gg.bidavid.lv2_z2.fragments.edit_add_fragment.PersonEditFragment
import gg.bidavid.lv2_z2.fragments.list_fragment.PersonListFragment
import gg.bidavid.lv2_z2.persistence.InspiringDatabase
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), UiClickListener, DatabaseEventListener {

    val quoteDao = InspiringDatabase.getInstance().quoteDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
        setUpPageChangeListener()
    }

    private fun initializeUI(){
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setUpPageChangeListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(newPosition: Int) {
                when(newPosition){
                    0 -> { val editFragment = (viewPager.adapter as ViewPagerAdapter).getItem(1) as PersonEditFragment
                           editFragment.recreateFragment() }
                    else-> return
                }
            }
        })
    }

    override fun onPersonImageClick(clickedPerson: InspiringPerson) {
        val clickedPersonQuoteList = quoteDao.getQuotesForPerson(clickedPerson.ID)
        Toast.makeText(MyApplication.ApplicationContext, clickedPersonQuoteList[Random.nextInt(0, clickedPersonQuoteList.size)].text + " ID: " + clickedPerson.ID.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onPersonEditClick(clickedPerson: InspiringPerson) {
        Toast.makeText(MyApplication.ApplicationContext, clickedPerson.firstName +" sent to edit", Toast.LENGTH_SHORT).show()
        val editFragment = (viewPager.adapter as ViewPagerAdapter).getItem(1) as PersonEditFragment
        editFragment.loadPerson(clickedPerson)
        viewPager.currentItem = 1
    }

    override fun onPersonAddClick() {
        viewPager.currentItem = 1
    }

    override fun onWritingEventOccured() {
        val listFragment = (viewPager.adapter as ViewPagerAdapter).getItem(0) as PersonListFragment
        listFragment.refreshRecyclerData()
        viewPager.currentItem = 0
    }


}
