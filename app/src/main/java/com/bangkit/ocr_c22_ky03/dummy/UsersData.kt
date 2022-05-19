package com.bangkit.ocr_c22_ky03.dummy

import com.bangkit.ocr_c22_ky03.R
import java.util.ArrayList

object UsersData {
    private val name = arrayOf(
        "Abdullah",
        "Rafi Aditya",
        "Erza Juan",
        )

    private val date = arrayOf(
        "01 Mei 2022 12:12",
        "02 Mei 2022 12:12",
        "03 Mei 2022 12:12",
    )

    private val status = arrayOf(
        "rejected",
        "on process",
        "verified",
    )


    val listData: ArrayList<Users>
        get() {
            val list = arrayListOf<Users>()
            for (position in name.indices) {
                val hero = Users()
                hero.name = name[position]
                hero.date = date[position]
                hero.status = status[position]
                list.add(hero)
            }
            return list
        }

}