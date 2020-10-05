package gg.bidavid.lv4_birdcounter

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {

    val birdCounter: MutableLiveData<Int> = MutableLiveData<Int>()
    val currentColourID: MutableLiveData<Int> = MutableLiveData<Int>()

    //Ako je viewModel unisten prilikom gasenja aplikacije, te ako se radi o prvom paljenju
    fun perfomRetrieveCheck(){
        if(this.birdCounter.value == null || this.currentColourID.value == null){
            this.retrieveData()
        }
    }

    fun incrementCounter(){
        this.birdCounter.value = this.birdCounter.value!!.plus(1)
    }

    fun setCurrentColour(colourID: Int){
        this.currentColourID.value = colourID
    }

    fun handleBirdClick(colourID: Int){
        this.incrementCounter()
        this.setCurrentColour(colourID)
    }

    fun resetData(){
        this.birdCounter.value = 0
        this.currentColourID.value = R.color.Transparent
    }

    fun retrieveData(){
        this.currentColourID.value = SharedPreferencesManager.retrieveColourID()
        this.birdCounter.value = SharedPreferencesManager.retrieveCounterValue()
    }

    //Ako korisnik ugasi aplikaciju, viewModel pri sljedecem paljenju nece moci odrzati stanje bez lokalne pohrane
    fun saveData(){
        SharedPreferencesManager.saveCounterValue(this.birdCounter.value!!)
        SharedPreferencesManager.saveColourID(this.currentColourID.value!!)
    }
}