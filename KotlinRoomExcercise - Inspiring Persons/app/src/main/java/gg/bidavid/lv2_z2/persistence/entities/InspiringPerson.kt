package gg.bidavid.lv2_z2.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_table")
class InspiringPerson (
    var firstName:String,
    var lastName:String,
    var description: String,
    var birthDate: String,
    var deathDate: String,
    var imageURIstring: String?){

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0
}

