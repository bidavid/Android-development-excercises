package gg.bidavid.lv2_z2.persistence

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gg.bidavid.lv2_z2.MyApplication
import gg.bidavid.lv2_z2.persistence.dao_interfaces.PersonDao
import gg.bidavid.lv2_z2.persistence.dao_interfaces.QuoteDao
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import gg.bidavid.lv2_z2.persistence.entities.Quote


@Database(entities = arrayOf(InspiringPerson::class, Quote::class), version = 1, exportSchema = false)
abstract class InspiringDatabase: RoomDatabase() {

    abstract fun personDao():PersonDao
    abstract fun quoteDao():QuoteDao

    companion object{
        private const val NAME = "inspiring_database"
        private var INSTANCE: InspiringDatabase? = null

        fun getInstance(): InspiringDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    MyApplication.ApplicationContext,
                    InspiringDatabase::class.java, NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as InspiringDatabase
        }
    }
}