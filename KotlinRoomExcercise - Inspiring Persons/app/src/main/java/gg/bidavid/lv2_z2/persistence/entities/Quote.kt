package gg.bidavid.lv2_z2.persistence.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "quote_table", foreignKeys = arrayOf(ForeignKey(entity = InspiringPerson::class,
    parentColumns = arrayOf("ID"),
    childColumns = arrayOf("personID"),
    onDelete = ForeignKey.CASCADE)))
class Quote(var text: String){

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0
    var personID: Long = 0
}