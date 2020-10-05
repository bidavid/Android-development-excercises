package gg.bidavid.lv2_z2.fragments.list_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import gg.bidavid.lv2_z2.MyApplication
import gg.bidavid.lv2_z2.UiClickListener
import gg.bidavid.lv2_z2.R
import gg.bidavid.lv2_z2.persistence.InspiringDatabase
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import kotlinx.android.synthetic.main.person_list_fragment.view.*

class PersonListFragment: Fragment() {

    //Activity slusa klikove na osobe, na edit gumbove, te na gumb za dodavanje novih
    //ne bih smio imati staticku referencu na nesto sto ima zivotni ciklus, pa ovo nije u companion objectu
    lateinit var uiClickListener: UiClickListener

    companion object{
        val personDao = InspiringDatabase.getInstance().personDao()

        //View za dohvacanje kontrola
        lateinit var fragmentView: View

        fun newInstance(): PersonListFragment {
            return PersonListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is UiClickListener){
            uiClickListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.person_list_fragment, container, false)
        setUpRecyclerView(fragmentView.recyclerView)
        setUpSwipeToRemove(fragmentView.recyclerView)
        setClickListeners()
        refreshRecyclerData()
        return fragmentView
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.ApplicationContext, RecyclerView.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(MyApplication.ApplicationContext, RecyclerView.VERTICAL))
        recyclerView.adapter = PersonAdapter(uiClickListener)
    }

    private fun setUpSwipeToRemove(recyclerView: RecyclerView) {
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or(ItemTouchHelper.RIGHT)){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPerson = (recyclerView.adapter as PersonAdapter).getPerson(viewHolder.adapterPosition)
                //Brisanje citata je odradeno kaskadnim ponasanjem u entitetu za citat, pa nije potrebno eksplicitno
                //brisati slogove citata koji u stranom kljucu sadrže id osobe koja se brise
                personDao.delete(swipedPerson)
                //refreshRecyclerData() - odabrana je linija ispod zbog animacije, ne zelimo notifyDataSetChanged() vec notifyItemRemoved()
                (recyclerView.adapter as PersonAdapter).removePerson(viewHolder.adapterPosition)
                Toast.makeText(activity, "Deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun setClickListeners() {
        fragmentView.btnAddPerson.setOnClickListener { uiClickListener.onPersonAddClick() }
    }

    fun refreshRecyclerData(){
        (fragmentView.recyclerView.adapter as PersonAdapter).refreshData(personDao.getAll())
    }







}