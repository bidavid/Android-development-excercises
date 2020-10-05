package gg.bidavid.lv4_birdcounter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import gg.bidavid.lv4_birdcounter.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var counterViewModel: CounterViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        this.counterViewModel = ViewModelProvider(this).get(CounterViewModel::class.java)
        this.binding.counterVM = this.counterViewModel
        this.binding.lifecycleOwner = this@MainActivity
        //connectObservers()
        this.counterViewModel.perfomRetrieveCheck()
        setUpClickListeners()
    }

    private fun connectObservers() {
        val clickObserver = Observer<Int> {
           // binding.invalidateAll()
        }
        counterViewModel.birdCounter.observe(this, clickObserver)
        counterViewModel.currentColourID.observe(this, clickObserver)
    }

    private fun setUpClickListeners() {
        btnRed.setOnClickListener { onBirdSelected(R.color.Red) }
        btnBlue.setOnClickListener { onBirdSelected(R.color.Blue) }
        btnGreen.setOnClickListener { onBirdSelected(R.color.Green) }
        btnYellow.setOnClickListener { onBirdSelected(R.color.Yellow) }
        btnReset.setOnClickListener { resetData() }
    }

    private fun onBirdSelected(colourID: Int){
        this.counterViewModel.handleBirdClick(colourID)
    }

    private fun resetData() {
        this.counterViewModel.resetData()
    }
    //Pohrana stanja u lokalnu pohranu, da ga ne izgubimo nakon gasenja aplikacije
    override fun onStop() {
        super.onStop()
        this.counterViewModel.saveData()
    }

}
