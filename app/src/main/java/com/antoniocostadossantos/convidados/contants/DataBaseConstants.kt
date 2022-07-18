package com.antoniocostadossantos.convidados.contants

class DataBaseConstants private constructor() {

    object GUEST {
        const val ID = "guestid"
        const val TABLE_NAME = "Guest"

        object COLUMS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }

}