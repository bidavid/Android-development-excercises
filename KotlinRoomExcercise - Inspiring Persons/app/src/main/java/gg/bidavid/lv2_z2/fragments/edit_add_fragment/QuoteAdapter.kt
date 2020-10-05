package gg.bidavid.lv2_z2.fragments.edit_add_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gg.bidavid.lv2_z2.R
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import gg.bidavid.lv2_z2.persistence.entities.Quote
import kotlinx.android.synthetic.main.quote_cell.view.*

class QuoteAdapter: RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    val quoteList: MutableList<Quote>

    init {
        this.quoteList = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val quoteView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_cell,parent,false)

        val quoteHolder = QuoteViewHolder(quoteView)
        return quoteHolder
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quoteList[position]
        holder.bindQuote(quote)
    }

    override fun getItemCount(): Int = quoteList.size

    fun addQuote(quote: Quote){
        quoteList.add(0,quote)
        notifyItemInserted(0)
    }

    fun loadQuotes(quotes: List<Quote>){
        quoteList.clear()
        quoteList.addAll(quotes)
        notifyDataSetChanged()
    }

    fun removeQuote(position: Int){
        quoteList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear(){
        quoteList.clear()
        notifyDataSetChanged()
    }

    class QuoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindQuote(quote: Quote){
            itemView.tvQuote.setText(quote.text)
        }
    }
}