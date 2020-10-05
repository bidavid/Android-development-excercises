package gg.bidavid.lv2_z2

import gg.bidavid.lv2_z2.persistence.entities.InspiringPerson

interface UiClickListener {
        fun onPersonImageClick(clickedPerson: InspiringPerson)
        fun onPersonEditClick(clickedPerson: InspiringPerson)
        fun onPersonAddClick()
}