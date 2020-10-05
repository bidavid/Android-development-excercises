package gg.bidavid.lv2_z2.fragments.list_fragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gg.bidavid.lv2_z2.UiClickListener
import gg.bidavid.lv2_z2.R
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import kotlinx.android.synthetic.main.person_cell.view.*

class PersonAdapter(clickListener: UiClickListener):RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    private val inspiringPersons: MutableList<InspiringPerson>
    private val clickListener: UiClickListener

    init {
        this.inspiringPersons = mutableListOf()
        this.clickListener = clickListener
    }

    fun refreshData(persons:List<InspiringPerson>){
        inspiringPersons.clear()
        inspiringPersons.addAll(persons)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val personView = LayoutInflater.from(parent.context).inflate(R.layout.person_cell,parent,false)

        val personHolder = PersonViewHolder(personView)
        return personHolder
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = inspiringPersons[position]
        holder.bindPerson(person, clickListener)
    }

    fun removePerson(position: Int){
        inspiringPersons.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getPerson(position: Int):InspiringPerson{
        return inspiringPersons[position]
    }

    override fun getItemCount(): Int = inspiringPersons.size

    class PersonViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bindPerson(person: InspiringPerson, clickListener: UiClickListener){
            itemView.tvPersonFirstName.setText(person.firstName)
            itemView.tvPersonLastName.setText(person.lastName)
            itemView.tvPersonBirthDate.setText(person.birthDate)
            itemView.tvPersonDeathDate.setText(person.deathDate)
            itemView.tvPersonDescription.setText(person.description)

            when(person.imageURIstring){
                null-> itemView.ivPersonPicture.setImageResource(R.drawable.person_shadow_icon)
                else -> itemView.ivPersonPicture.setImageURI(Uri.parse(person.imageURIstring))
            }

            itemView.ivPersonPicture.setOnClickListener{ clickListener.onPersonImageClick(person)}
            itemView.ivEditButton.setOnClickListener{ clickListener.onPersonEditClick(person)}
        }
    }
}