package net.gibisoft.talamonti.entities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase


class CassettoController(val context: Context?) {

    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): CassettoController {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun save(cassetto:Cassetto) {
        val contentValue = ContentValues()
        contentValue.put("scaffale", cassetto.scaffale)
        contentValue.put("posizione", cassetto.posizione)
        contentValue.put("capacita", cassetto.capacita)
        database!!.replace("cassetti", null, contentValue)
    }

    fun lista(): List<Cassetto> {
        val result = ArrayList<Cassetto>()
        val columns = arrayOf("scaffale", "posizione", "capacita", "numpezzi")
        database!!.query(
            "cassetti_view", columns, null, null, null, null, null
        ).use {
            while (it.moveToNext()) {
                result.add(
                    Cassetto(
                        it.getString(0),
                        it.getInt(1),
                        it.getInt(2),
                        it.getInt(3)
                    )
                )
            }
        }
        return result
    }

    fun listaScaffale(scaffale: String): List<Cassetto> {
        val result = ArrayList<Cassetto>()
        val columns = arrayOf("scaffale", "posizione", "capacita", "numpezzi")
        database!!.query(
            "cassetti_view", columns, "scaffale=?", arrayOf(scaffale), null, null, "posizione"
        ).use {
            while (it.moveToNext()) {
                result.add(
                    Cassetto(
                        it.getString(0),
                        it.getInt(1),
                        it.getInt(2),
                        it.getInt(3)
                    )
                )
            }
        }
        return result
    }

    fun delete(scaffale: String?) {
        database!!.delete("cassetto", "scaffale=?", arrayOf(scaffale))
    }

    fun load(scaffale: String, posizione: Int): Cassetto {
        val columns = arrayOf("scaffale", "posizione", "capacita", "numpezzi")
        val args = arrayOf(scaffale, posizione.toString())
        database!!.query(
            "cassetti_view", columns, "scaffale=? AND posizione=?", args, null, null, null
        ).use {
            it.moveToFirst()
            return Cassetto(
                it.getString(0),
                it.getInt(1),
                it.getInt(2),
                it.getInt(3)
            );
        }
    }

    companion object {

        fun newInstance(context: Context?): CassettoController {
            return CassettoController(context).open()
        }

        fun init_table(context: Context?) {
            val cassettoController = CassettoController.newInstance(context)
            cassettoController.save(Cassetto("S001", 1, 2))
            cassettoController.save(Cassetto("S001", 2, 3))
            cassettoController.save(Cassetto("S001", 3, 4))
            cassettoController.save(Cassetto("S001", 4, 3))
            cassettoController.save(Cassetto("S001", 5, 2))
            cassettoController.save(Cassetto("S001", 6, 2))
            cassettoController.save(Cassetto("S001", 7, 3))
            cassettoController.save(Cassetto("S001", 8, 4))
            cassettoController.save(Cassetto("S001", 9, 3))
            cassettoController.save(Cassetto("S001", 10, 2))
            cassettoController.save(Cassetto("S002", 1, 2))
            cassettoController.save(Cassetto("S002", 2, 1))
            cassettoController.save(Cassetto("S002", 3, 1))
            cassettoController.save(Cassetto("S002", 4, 3))
            cassettoController.save(Cassetto("S002", 5, 2))
            cassettoController.save(Cassetto("S002", 6, 1))
            cassettoController.save(Cassetto("S002", 7, 1))
            cassettoController.save(Cassetto("S002", 8, 2))
            cassettoController.save(Cassetto("S002", 9, 3))
            cassettoController.save(Cassetto("S002", 10, 1))
            cassettoController.close()
        }
    }

}