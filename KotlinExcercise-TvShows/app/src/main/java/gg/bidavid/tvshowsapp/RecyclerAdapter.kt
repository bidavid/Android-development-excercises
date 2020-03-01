package gg.bidavid.tvshowsapp

import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gg.bidavid.tvshowsapp.model.SearchResult
import kotlinx.android.synthetic.main.tv_show_cell.view.*

class RecyclerAdapter(clickListener: ClickListener): RecyclerView.Adapter<RecyclerAdapter.TvShowViewHolder>() {
    val clickListener:ClickListener
    val tvShowsList :MutableList<SearchResult> = mutableListOf()

    init {
        this.clickListener=clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val cellView = LayoutInflater.from(parent.context).inflate(R.layout.tv_show_cell,parent,false)
        return TvShowViewHolder(cellView,clickListener)
    }
    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.setPoster(tvShowsList.get(position))
    }
    override fun getItemCount(): Int = tvShowsList.size
    fun addData(newShowsList: List<SearchResult>){
        this.tvShowsList.clear()
        this.tvShowsList.addAll(newShowsList)
        notifyDataSetChanged()
    }




    class TvShowViewHolder(itemView: View, clickListener: ClickListener): RecyclerView.ViewHolder(itemView) {
        val clickListener:ClickListener

        init {
            this.clickListener=clickListener
            itemView.ivShowPoster.setOnClickListener{ clickListener.OnPosterClick(adapterPosition) }
        }

        fun setPoster(result: SearchResult){
            val url = result.show.poster?.mediumURL ?: null
            Picasso.get().load(url).placeholder(R.mipmap.ic_launcher).into(itemView.ivShowPoster)
        }

    }
}