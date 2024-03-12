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
    private fun open(): UtensileController {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    private fun close() {
        dbHelper!!.close()
    }

    private fun load(codice: String): Utensile {
        val columns = arrayOf("codice", "descrizione", "scaffale", "posizione")
        database!!.query(
            "utensili", columns, "codice=?", arrayOf(codice), null, null, null
        ).use {
            if (it.moveToFirst()) {
                return Utensile(
                    it.getString(0),
                    it.getString(1),
                    it.getString(2),
                    it.getInt(3)
                )
            } else {
                return Utensile(codice, "", "", 0)
            }
        }
    }

    private fun save(utensile: Utensile) {
        val contentValue = ContentValues()
        contentValue.put("codice", utensile.codice)
        contentValue.put("descrizione", utensile.descrizione)
        contentValue.put("scaffale", utensile.scaffale)
        contentValue.put("posizione", utensile.posizione)
        database!!.replace("utensili", null, contentValue)
    }

    private fun delete(codice: String?) {
        database!!.delete("utensili", "codice=?", arrayOf(codice))
    }


    private fun lista(): List<Utensile> {
        val result = ArrayList<Utensile>()
        val columns = arrayOf("codice", "descrizione", "scaffale", "posizione")
        database!!.query(
            "utensili", columns, null, null, null, null, "codice"
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


    companion object {

        fun newInstance(context: Context?): UtensileController {
            return UtensileController(context).open()
        }

        fun load(context: Context?, codice: String): Utensile {
            with(newInstance(context)) {
                return this.load(codice)
            }
        }

        fun save(context: Context?, utensile: Utensile) {
            with(newInstance(context)) {
                this.save(utensile)
            }
        }

        fun delete(context: Context?, codice: String?) {
            with(newInstance(context)) {
                this.delete(codice)
            }
        }

        fun lista(context: Context?): List<Utensile> {
            with(newInstance(context)) {
                return this.lista()
            }
        }


        fun init_table(context: Context?) {
            if (lista(context).isEmpty()) {
                with(newInstance(context)) {
                    this.save(Utensile("U01", "prova utensile 1", "S001", 1))
                    this.save(Utensile("U02", "prova utensile 2", "S001", 1))
                    this.save(Utensile("U03", "prova utensile 3", "S001", 2))
                    this.save(Utensile("U04", "prova utensile 4", "S001", 3))
                    this.save(Utensile("U05", "prova utensile 5", "S001", 3))
                    this.save(Utensile("U06", "prova utensile 6", "S002", 1))
                    this.save(Utensile("U07", "prova utensile 7", "S002", 1))
                    this.save(Utensile("U08", "prova utensile 8", "S002", 2))
                    this.save(Utensile("U09", "prova utensile 9", "S002", 3))
                    this.save(Utensile("U10", "prova utensile 10", "S002", 3))
                    this.close()
                }
            }
        }
    }

}