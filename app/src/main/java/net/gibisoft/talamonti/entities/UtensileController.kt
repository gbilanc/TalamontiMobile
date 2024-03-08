package net.gibisoft.talamonti.entities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class UtensileController(val context: Context?) {

    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): UtensileController {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun save(utensile:Utensile) {
        val contentValue = ContentValues()
        contentValue.put("codice", utensile.codice)
        contentValue.put("descrizione", utensile.descrizione)
        contentValue.put("scaffale", utensile.scaffale)
        contentValue.put("posizione", utensile.posizione)
        database!!.replace("utensili", null, contentValue)
    }

    fun lista(): List<Utensile> {
        val result = ArrayList<Utensile>()
        val columns = arrayOf("codice", "descrizione", "scaffale", "posizione")
        database!!.query(
            "utensili", columns, null, null, null, null, null
        ).use {
            while (it.moveToNext()) {
                result.add(
                    Utensile(
                        it.getString(0),
                        it.getString(1),
                        it.getString(2),
                        it.getInt(3)
                    )
                )
            }
        }
        return result
    }

    fun lista(desc: String): List<Utensile> {
        val result = ArrayList<Utensile>()
        val columns = arrayOf("codice", "descrizione", "scaffale", "posizione")
        database!!.query(
            "utensili", columns, "descrizione LIKE %?%", arrayOf(desc), null, null, "descrizione"
        ).use {
            while (it.moveToNext()) {
                result.add(
                    Utensile(
                        it.getString(0),
                        it.getString(1),
                        it.getString(2),
                        it.getInt(3)
                    )
                )
            }
        }
        return result
    }

    fun delete(codice: String?) {
        database!!.delete("utensili", "codice=?", arrayOf(codice))
    }

    fun load(codice: String): Utensile {
        val columns = arrayOf("codice", "descrizione", "scaffale", "posizione")
        database!!.query(
            "utensili", columns, "codice=?", arrayOf(codice), null, null, null
        ).use {
            it.moveToFirst()
            return Utensile(
                it.getString(0),
                it.getString(1),
                it.getString(2),
                it.getInt(3)
            )
        }
    }

    companion object {

        fun newInstance(context: Context?): UtensileController {
            return UtensileController(context).open()
        }

        fun init_table(context: Context?) {
            val utensileController = UtensileController.newInstance(context)
            utensileController.save(Utensile("U01", "prova utensile 1", "S001", 1))
            utensileController.save(Utensile("U02", "prova utensile 2", "S001", 1))
            utensileController.save(Utensile("U03", "prova utensile 3", "S001", 2))
            utensileController.save(Utensile("U04", "prova utensile 4", "S001", 3))
            utensileController.save(Utensile("U05", "prova utensile 5", "S001", 3))
            utensileController.save(Utensile("U06", "prova utensile 6", "S002", 1))
            utensileController.save(Utensile("U07", "prova utensile 7", "S002", 1))
            utensileController.save(Utensile("U08", "prova utensile 8", "S002", 2))
            utensileController.save(Utensile("U09", "prova utensile 9", "S002", 3))
            utensileController.save(Utensile("U10", "prova utensile 10", "S002", 3))
            utensileController.close()
        }
    }

}