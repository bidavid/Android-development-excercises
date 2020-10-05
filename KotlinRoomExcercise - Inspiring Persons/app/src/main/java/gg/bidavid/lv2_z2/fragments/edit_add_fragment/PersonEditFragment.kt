package gg.bidavid.lv2_z2.fragments.edit_add_fragment

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import gg.bidavid.lv2_z2.MyApplication
import gg.bidavid.lv2_z2.DatabaseEventListener
import gg.bidavid.lv2_z2.R
import gg.bidavid.lv2_z2.persistence.InspiringDatabase
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import gg.bidavid.lv2_z2.persistence.entities.Quote
import kotlinx.android.synthetic.main.person_edit_fragment.view.*
import java.util.*

class PersonEditFragment: Fragment() {

    //Activity osluskuje izmjene u bazi podataka
    lateinit var databaseEventListener: DatabaseEventListener

    companion object {
        val personDao = InspiringDatabase.getInstance().personDao()
        val quoteDao = InspiringDatabase.getInstance().quoteDao()

        //View za dohvacanje kontrola
        lateinit var fragmentView: View

        //Osoba za editanje, ucitava se u load funkciji
        var currentlyEditedPerson: InspiringPerson? = null

        //Nacin rada odreduje funkciju i ikonicu buttona za spremanje
        private val EDIT_MODE = "EDIT_MODE_CODE"
        private val ADD_MODE = "ADD_MODE_CODE"
        private var current_mode = ADD_MODE

        fun newInstance(): PersonEditFragment {
            return PersonEditFragment()
        }

        private var currentImageURI: Uri? = null

        private val IMAGE_PICK_CODE = 1000;
        private val PERMISSION_CODE = 1001;

        private var dateTypeSelected: Int? = null
        private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is DatabaseEventListener){
            databaseEventListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.person_edit_fragment, container, false)
        setUpRecyclerView()
        setClickListeners()
        setUpSwipeToRemove(fragmentView.rvQuotes)
        return fragmentView
    }

    private fun setUpRecyclerView(){
        fragmentView.rvQuotes.layoutManager = LinearLayoutManager(MyApplication.ApplicationContext, RecyclerView.VERTICAL, false)
        fragmentView.rvQuotes.itemAnimator = DefaultItemAnimator()
        fragmentView.rvQuotes.addItemDecoration(DividerItemDecoration(MyApplication.ApplicationContext, RecyclerView.VERTICAL))
        fragmentView.rvQuotes.adapter = QuoteAdapter()
    }

    private fun setClickListeners() {
        fragmentView.tvBirthDate.setOnClickListener {
            dateTypeSelected = it.id
            showCalendar()
        }

        fragmentView.tvDeathDate.setOnClickListener {
            dateTypeSelected = it.id
            showCalendar()
        }

        dateSetListener = object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                var date:String = dayOfMonth.toString() + "/" + (month+1).toString() + "/" + year.toString()
                when(dateTypeSelected){
                    R.id.tvBirthDate -> fragmentView.tvBirthDate.text = date
                    R.id.tvDeathDate -> fragmentView.tvDeathDate.text = date
                }
            }
        }

        fragmentView.btnAddQuote.setOnClickListener{addQuoteToRecycler()}

        fragmentView.ivPickImage.setOnClickListener { startGallery() }

        fragmentView.btnSavePerson.setOnClickListener { determineAppropriateAction() }
    }

    private fun setUpSwipeToRemove(quoteRecycler: RecyclerView){
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or(ItemTouchHelper.RIGHT)){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (quoteRecycler.adapter as QuoteAdapter).removeQuote(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(quoteRecycler)
    }

    private fun showCalendar(){
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(this.requireContext(), android.R.style.Theme_Material_Light_Dialog_MinWidth,
            dateSetListener, year, month, day)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.show()
    }

    private fun addQuoteToRecycler(){
        val quoteText = fragmentView.etQuote.text.toString().trim()
        if(quoteText.length==0){
            fragmentView.etQuote.setError("Please enter some text here")
            return
        }else{
            fragmentView.etQuote.setText("")
            (fragmentView.rvQuotes.adapter as QuoteAdapter).addQuote(Quote(quoteText))
        }
    }

    private fun startGallery(){
        //Provjeri runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (MyApplication.ApplicationContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //Permission odbijen
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //Prikazi pop up
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else{
                //Permission vec dan
                pickImageFromGallery();
            }
        }
        else{
            // OS < Marshmallow
            pickImageFromGallery();
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Pop up prihvacen
                    pickImageFromGallery()
                }
                else{
                    //Pop up odbijen
                    Toast.makeText(MyApplication.ApplicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }}

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            currentImageURI = data?.data
            fragmentView.ivPickImage.setImageURI(currentImageURI)
        }
    }

    fun loadPerson(clickedPerson: InspiringPerson){
        current_mode = EDIT_MODE
        fragmentView.btnSavePerson.setImageResource(R.drawable.ic_save)

        currentlyEditedPerson = clickedPerson

        fragmentView.etFirstName.setText(currentlyEditedPerson!!.firstName)
        fragmentView.etLastName.setText(currentlyEditedPerson!!.lastName)
        fragmentView.tvBirthDate.setText(currentlyEditedPerson!!.birthDate)
        fragmentView.tvDeathDate.setText(currentlyEditedPerson!!.deathDate)
        fragmentView.etDescription.setText(currentlyEditedPerson!!.description)

        when(currentlyEditedPerson!!.imageURIstring){
            null -> fragmentView.ivPickImage.setImageResource(R.drawable.ic_add_photo)
            else ->{fragmentView.ivPickImage.setImageURI(Uri.parse(clickedPerson.imageURIstring))
                    currentImageURI = Uri.parse(currentlyEditedPerson!!.imageURIstring)
            }
        }
        (fragmentView.rvQuotes.adapter as QuoteAdapter).loadQuotes(quoteDao.getQuotesForPerson(currentlyEditedPerson!!.ID))

    }

    //Pozvana svaki put kada korisnik navigira na fragment s listom osoba
    fun recreateFragment(){
        current_mode = ADD_MODE
        currentlyEditedPerson = null
        currentImageURI = null
        fragmentView.etFirstName.text.clear()
        fragmentView.etLastName.text.clear()
        fragmentView.tvBirthDate.setText("")
        fragmentView.tvDeathDate.setText("")
        fragmentView.etDescription.text.clear()
        fragmentView.etQuote.text.clear()
        fragmentView.ivPickImage.setImageResource(R.drawable.ic_add_photo)
        fragmentView.btnSavePerson.setImageResource(R.drawable.ic_done)
        (fragmentView.rvQuotes.adapter as QuoteAdapter).clear()
    }

    private fun determineAppropriateAction() {
        when(current_mode){
            EDIT_MODE -> savePersonToDatabase()
            ADD_MODE -> addPersonToDatabase()
        }
    }

    private fun savePersonToDatabase(){
        if (checkIfPersonIsReady()){
            currentlyEditedPerson!!.firstName = fragmentView.etFirstName.text.trim().toString()
            currentlyEditedPerson!!.lastName = fragmentView.etLastName.text.trim().toString()
            currentlyEditedPerson!!.description = fragmentView.etDescription.text.trim().toString()
            currentlyEditedPerson!!.birthDate = fragmentView.tvBirthDate.text.trim().toString()
            //Death date nije obavezan
            if(fragmentView.tvDeathDate.text.trim().length == 0){
                currentlyEditedPerson!!.deathDate = "/"
            }else{
                currentlyEditedPerson!!.deathDate = fragmentView.tvDeathDate.text.trim().toString()
            }
            currentlyEditedPerson!!.imageURIstring = currentImageURI?.toString()

            //Edit dovrsen, obrisi sve stare citate ove osobe iz baze
            quoteDao.deleteQuotesForPerson(currentlyEditedPerson!!.ID)

            //Svim citatima iz recyclera postavi foreign key (zato jer ima novih citata kojima je on pri stvaranju postavljen na 0), te svaki citat odmah insertaj u bazu
            for (quote in (fragmentView.rvQuotes.adapter as QuoteAdapter).quoteList){
                quote.personID = currentlyEditedPerson!!.ID
                quoteDao.insert(quote)
            }
            // Napravi update osobe
            personDao.update(currentlyEditedPerson!!)

            //Obavijesti activity -> fragment se resetira, listFragment postaje aktivan, recycler listFragmenta se refresha
            notifyRepoDataListener()
        }else return
    }

    private fun addPersonToDatabase(){
        if(checkIfPersonIsReady()){
            //Insert vraca autogenerirani id osobe
            var newPersonID = personDao.insert(createInspiringPerson())
            //Svakom citatu treba postaviti strani kljuc na id osobe kojoj pripada (da ne ostane 0), a zatim ju dodati u bazu podataka
            for (quote in (fragmentView.rvQuotes.adapter as QuoteAdapter).quoteList){
                quote.personID = newPersonID
                quoteDao.insert(quote)
            }
            Toast.makeText(MyApplication.ApplicationContext, "Person added", Toast.LENGTH_SHORT).show()
            //Osoba dodana, obavijesti prvi fragment
            notifyRepoDataListener()
        }else return
    }

    private fun notifyRepoDataListener(){
        databaseEventListener.onWritingEventOccured()
    }

    private fun checkIfPersonIsReady():Boolean{
        var personReadyForCreation: Boolean?
        when{
            fragmentView.etFirstName.text.trim().length == 0 -> {
                personReadyForCreation = false
                fragmentView.etFirstName.setError("Please provide first name")
            }
            fragmentView.etLastName.text.trim().length == 0 -> {
                personReadyForCreation = false
                fragmentView.etLastName.setError("Please provide last name")
            }
            fragmentView.tvBirthDate.text.trim().length == 0 -> {
                personReadyForCreation = false
                fragmentView.tvBirthDate.setError("Birth date is obligatory")
            }
            fragmentView.etDescription.text.trim().length == 0 -> {
                personReadyForCreation = false
                fragmentView.etDescription.setError("Please provide some kind of description")
            }
            (fragmentView.rvQuotes.adapter as QuoteAdapter).itemCount < 1 ->{
                personReadyForCreation = false
                Toast.makeText(MyApplication.ApplicationContext, "Please provide at least one quote for a new person", Toast.LENGTH_SHORT).show()
            }
            else -> personReadyForCreation = true
        }

        return personReadyForCreation
    }

    private fun createInspiringPerson(): InspiringPerson{
        var firstName = fragmentView.etFirstName.text.trim().toString()
        var lastName = fragmentView.etLastName.text.trim().toString()
        var description = fragmentView.etDescription.text.trim().toString()
        var birthDate = fragmentView.tvBirthDate.text.trim().toString()
        lateinit var deathDate: String

        if(fragmentView.tvDeathDate.text.trim().length == 0){
            deathDate = "/"
        }else{
            deathDate = fragmentView.tvDeathDate.text.trim().toString()
        }

        return InspiringPerson(firstName,lastName,description,birthDate,deathDate, currentImageURI?.toString() )
    }

}