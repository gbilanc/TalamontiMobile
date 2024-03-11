package net.gibisoft.talamonti.entities

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class ScaffaleController {

    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    private fun open(context: Context?): ScaffaleController {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    private fun close() {
        dbHelper!!.close()
    }

    private fun load(codice: String): Scaffale {
        val columns = arrayOf("codice", "indirizzo", "porta")
        database!!.query(
            "scaffali", columns, "codice=?", arrayOf(codice), null, null, null
        ).use {
            if( it.moveToFirst()){
                return Scaffale(
                    it.getString(0),
                    it.getString(1),
                    it.getInt(2)
                )
            }else{
                return Scaffale(codice,"",9600)
            }
        }
    }

    private fun save(scaffale: Scaffale) {
        val contentValue = ContentValues()
        contentValue.put("codice", scaffale.codice)
        contentValue.put("indirizzo", scaffale.indirizzo)
        contentValue.put("porta", scaffale.porta)
        database!!.replace("scaffali", null, contentValue)
        scaffale.cassetti!!.forEach {
            contentValue.clear()
            contentValue.put("scaffale", it.scaffale)
            contentValue.put("posizione", it.posizione)
            contentValue.put("capacita", it.capacita)
            database!!.replace("cassetti", null, contentValue)
        }
    }

    private fun delete(codice: String?) {
        database!!.delete("scaffali", "codice=?", arrayOf(codice))
        database!!.delete("cassetti", "scaffale=?", arrayOf(codice))
    }

    private fun lista(): List<Scaffale> {
        val result = ArrayList<Scaffale>()
        val columns = arrayOf("codice", "indirizzo", "porta")
        database!!.query(
            "scaffali", columns, null, null, null, null, "codice"
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

    private fun lista_cassetti(scaffale: String): Array<Cassetto> {
        val result = Array(20) { index ->
            Cassetto(scaffale, index + 1, 1, 0)
        }
        val columns = arrayOf("scaffale", "posizione", "capacita", "numpezzi")
        database!!.query(
            "cassetti_view", columns, "scaffale=?", arrayOf(scaffale), null, null, "posizione"
        ).use {
            while (it.moveToNext()) {
                result[it.getInt(1) - 1] =
                    Cassetto(
                        it.getString(0),
                        it.getInt(1),
                        it.getInt(2),
                        it.getInt(3)
                    )
            }
        }
        return result
    }


    companion object {

        fun newInstance(context: Context?): ScaffaleController {
            return ScaffaleController().open(context)
        }

        fun load(context: Context?, codice: String): Scaffale {
            with(newInstance(context)) {
                with(load(codice)) {
                    cassetti = lista_cassetti(codice)
                    return this
                }
            }
        }

        fun save(context: Context?, scaffale: Scaffale) {
            with(newInstance(context)) {
                this.save(scaffale)
            }
        }

        fun delete(context: Context?, codice: String?) {
            with(newInstance(context)) {
                this.delete(codice)
            }
        }

        fun lista(context: Context?): List<Scaffale> {
            with(newInstance(context)) {
                return this.lista()
            }
        }

        fun init_table(context: Context?) {
            if (lista(context).isEmpty()) {
                with(newInstance(context)) {
                    this.save(Scaffale("S001", "192.168.0.1", 9600))
                    this.save(Scaffale("S002", "192.168.0.2", 9600))
                    this.close()
                }
            }
        }
    }

}