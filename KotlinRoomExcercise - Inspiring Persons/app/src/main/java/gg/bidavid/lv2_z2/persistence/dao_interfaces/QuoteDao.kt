package gg.bidavid.lv2_z2.persistence.dao_interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson
import gg.bidavid.lv2_z2.persistence.entities.Quote

@Dao
interface QuoteDao {

    @Insert
    fun insert(quote: Quote): Long

    @Delete
    fun delete(quote: Quote)

    @Query("SELECT * FROM quote_table WHERE quote_table.personID = :personID")
    fun getQuotesForPerson(personID: Long): List<Quote>

    @Query("DELETE FROM quote_table WHERE quote_table.personID = :personID")
    fun deleteQuotesForPerson(personID: Long)
}