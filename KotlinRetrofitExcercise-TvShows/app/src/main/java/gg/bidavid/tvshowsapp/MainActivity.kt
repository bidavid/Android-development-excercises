package gg.bidavid.tvshowsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import gg.bidavid.tvshowsapp.model.SearchResult
import gg.bidavid.tvshowsapp.networking.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val POSITION_KEY: String = "POSITION"
const val RESPONSE_LIST_KEY : String = "LIST"

class MainActivity : AppCompatActivity(), ClickListener {

    lateinit var searchResultList: ArrayList<SearchResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycler()
        setUpListeners()
    }

    private fun setUpRecycler() {
        recyclerView.layoutManager= GridLayoutManager(this, 4)
        recyclerView.adapter= RecyclerAdapter(this)
    }

    private fun setUpListeners() {
        btnSearch.setOnClickListener{findShows()}
    }

    private fun findShows() {
        val title = etTVshowName.text.toString().trim()

        NetworkUtils.apiInterface.getTvShows(title).enqueue(object: Callback<List<SearchResult>>{
                    override fun onFailure(call: Call<List<SearchResult>>, t: Throwable) {
                        showToast(t.message)
                    }

                    override fun onResponse(call: Call<List<SearchResult>>, response: Response<List<SearchResult>>) {
                       if(response.isSuccessful && response.body()!= null){
                           showToast("Successfull")
                           searchResultList=ArrayList(response.body()!!)
                           setUpRecyclerData(searchResultList)
                       }
                    }

                })

    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun setUpRecyclerData(retrievedShows: List<SearchResult>) {
        (recyclerView.adapter as RecyclerAdapter).addData(retrievedShows)
    }

    override fun OnPosterClick(position: Int) {
        Toast.makeText(this, "$position",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TvShowDetailsActivity::class.java)
        intent.putExtra(POSITION_KEY, position)
        intent.putExtra(RESPONSE_LIST_KEY, searchResultList) //RADI OVOG SE RUSI, jer nisam implementirao serializable u klase
        startActivity(intent)
    }

}
