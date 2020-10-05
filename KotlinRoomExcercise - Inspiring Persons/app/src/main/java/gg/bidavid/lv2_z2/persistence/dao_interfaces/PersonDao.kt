package gg.bidavid.lv2_z2.persistence.dao_interfaces

import androidx.room.*
import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson

@Dao
interface PersonDao {

    @Insert
    fun insert(person: InspiringPerson): Long

    @Update
    fun update(person: InspiringPerson)

    @Delete
    fun delete(person: InspiringPerson)

    @Query("DELETE FROM people_table")
    fun deleteAll()

    @Query("SELECT * FROM people_table")
    fun getAll(): List<InspiringPerson>

    @Query("SELECT * FROM people_table where people_table.ID = :id")
    fun getPersonByID(id: Long): InspiringPerson
}