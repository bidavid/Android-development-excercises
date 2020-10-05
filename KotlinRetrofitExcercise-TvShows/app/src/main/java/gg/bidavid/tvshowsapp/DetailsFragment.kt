package gg.bidavid.tvshowsapp
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import gg.bidavid.tvshowsapp.model.SearchResult
import kotlinx.android.synthetic.main.tv_show_fragment.*

private val BUNDLE_MESSAGE = "View arguments"

class DetailsFragment: Fragment(){

    private var itemForDisplay:SearchResult? = null

    companion object{
        fun getInstance(item:SearchResult)=DetailsFragment().apply{
            arguments = Bundle().apply { putSerializable(BUNDLE_MESSAGE, item) }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        itemForDisplay = arguments?.getSerializable(BUNDLE_MESSAGE) as SearchResult
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val createdView = inflater.inflate((R.layout.tv_show_fragment),container,false)

        return createdView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        tvTitle.setText(itemForDisplay?.show?.title)
        tvPremiered.setText(itemForDisplay?.show?.premiereDate)
        tvSummary.setText(itemForDisplay?.show?.summary)
        val url= itemForDisplay?.show?.poster?.mediumURL
        Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(ivPoster)
    }
}