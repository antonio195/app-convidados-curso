package com.antoniocostadossantos.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.antoniocostadossantos.convidados.contants.DataBaseConstants
import com.antoniocostadossantos.convidados.model.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context) {

    private val guestDatabase = GuestDataBase(context)

    companion object {
        // Singleton
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDatabase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put("presence", presence)
            values.put("name", guest.name)

            db.insert("Guest", null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDatabase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put("presence", presence)
            values.put("name", guest.name)

            val selection = "id = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDatabase.writableDatabase
            val selection = "id = ?"
            val args = arrayOf(id.toString())
            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun get(id: Int): GuestModel? {

        var guest: GuestModel? = null

        try {
            val db = guestDatabase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMS.ID,
                DataBaseConstants.GUEST.COLUMS.NAME,
                DataBaseConstants.GUEST.COLUMS.PRESENCE
            )

            val selection = "id = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args,
                null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.PRESENCE))

                    guest = GuestModel(id, name, presence == 1)

                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guest
        }
        return guest
    }

    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase
            val selection = arrayOf(
                DataBaseConstants.GUEST.COLUMS.ID,
                DataBaseConstants.GUEST.COLUMS.NAME,
                DataBaseConstants.GUEST.COLUMS.PRESENCE
            )


            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME, selection, null, null,
                null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))

                }
            }

            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMS.ID,
                DataBaseConstants.GUEST.COLUMS.NAME,
                DataBaseConstants.GUEST.COLUMS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMS.PRESENCE + " = ?"
            val args = arrayOf("1")

            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

//            val cursor = db.query(
//                DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args,
//                null, null, null
//            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))

                }
            }

            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }

    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMS.ID,
                DataBaseConstants.GUEST.COLUMS.NAME,
                DataBaseConstants.GUEST.COLUMS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMS.PRESENCE + " = ?"
            val args = arrayOf("1")

            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

//            val cursor = db.query(
//                DataBaseConstants.GUEST.TABLE_NAME, projection, selection, args,
//                null, null, null
//            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))

                }
            }

            cursor.close()

        } catch (e: Exception) {
            return list
        }
        return list
    }


}