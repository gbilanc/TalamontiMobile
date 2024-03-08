package net.gibisoft.talamonti.entities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class ScaffaleController(val context: Context?) {

    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): ScaffaleController {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun save(scaffale:Scaffale) {
        val contentValue = ContentValues()
        contentValue.put("codice", scaffale.codice)
        contentValue.put("indirizzo", scaffale.indirizzo)
        contentValue.put("porta", scaffale.porta)
        database!!.replace("scaffali", null, contentValue)
    }

    fun lista(): List<Scaffale> {
        val result = ArrayList<Scaffale>()
        val columns = arrayOf("codice", "indirizzo", "porta")
        database!!.query(
            "scaffali", columns, null, null, null, null, null
        ).use {
            while (it.moveToNext()) {
                result.add(
                    Scaffale(
                        it.getString(0),
                        it.getString(1),
                        it.getInt(2)
                    )
                )
            }
        }
        return result
    }

    fun load(codice: String): Scaffale {
        val columns = arrayOf("codice", "indirizzo", "porta")
        database!!.query(
            "scaffali", columns, "codice=?", arrayOf(codice), null, null, null
        ).use {
            it.moveToFirst()
            return Scaffale(
                it.getString(0),
                it.getString(1),
                it.getInt(2)
            )
        }
    }
    fun delete(codice: String?) {
        database!!.delete("scaffali", "codice=?", arrayOf(codice))
    }

    companion object {

        fun newInstance(context: Context?): ScaffaleController {
            return ScaffaleController(context).open()
        }

        fun init_table(context: Context?) {
            val scaffaleController = newInstance(context)
            scaffaleController.save(Scaffale("S001", "192.168.0.1", 9600))
            scaffaleController.save(Scaffale("S002", "192.168.0.2", 9600))
            scaffaleController.close()
        }
    }

}