package gg.bidavid.lv4_birdcounter

import android.content.Context

object SharedPreferencesManager {

    const val FILE_NAME = "LV4BirdCounterPreferences"
    const val PREFS_KEY_COUNTER_VALUE = "Saved_counter_value"
    const val PREFS_KEY_COLOUR_ID = "Saved_colour_id"

    val sharedPreferences = MyApplication.ApplicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()


    fun saveColourID(colourID: Int) {
        editor.putInt(PREFS_KEY_COLOUR_ID, colourID)
        editor.apply()
    }

    fun saveCounterValue(counterValue: Int) {
        editor.putInt(PREFS_KEY_COUNTER_VALUE, counterValue)
        editor.apply()
    }

    fun retrieveColourID(): Int {
        return sharedPreferences.getInt(PREFS_KEY_COLOUR_ID, R.color.Transparent)
    }

    fun retrieveCounterValue(): Int {
        return sharedPreferences.getInt(PREFS_KEY_COUNTER_VALUE, 0)
    }

}