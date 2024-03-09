package net.gibisoft.talamonti.entities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase


class CassettoController(private val context: Context?) {

    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    private fun open(): CassettoController {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    private fun close() {
        dbHelper!!.close()
    }

    private fun load(scaffale: String, posizione: Int): Cassetto {
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

    private fun save(cassetto: Cassetto) {
        val contentValue = ContentValues()
        contentValue.put("scaffale", cassetto.scaffale)
        contentValue.put("posizione", cassetto.posizione)
        contentValue.put("capacita", cassetto.capacita)
        database!!.replace("cassetti", null, contentValue)
    }

    private fun delete(scaffale: String?) {
        database!!.delete("cassetto", "scaffale=?", arrayOf(scaffale))
    }

    private fun lista(): List<Cassetto> {
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

    private fun listaScaffale(scaffale: String): Array<Cassetto?> {
        val result = arrayOfNulls<Cassetto>(20)
        val columns = arrayOf("scaffale", "posizione", "capacita", "numpezzi")
        database!!.query(
            "cassetti_view", columns, "scaffale=?", arrayOf(scaffale), null, null, "posizione"
        ).use {
            while (it.moveToNext()) {
                result[it.getInt(1)]=
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

        fun newInstance(context: Context?): CassettoController {
            return CassettoController(context).open()
        }

        fun load(context: Context?, scaffale: String, posizione: Int): Cassetto {
            with(newInstance(context)) {
                return this.load(scaffale, posizione)
            }
        }

        fun save(context: Context?, cassetto: Cassetto) {
            with(newInstance(context)) {
                this.save(cassetto)
            }

        }

        fun delete(context: Context?, scaffale: String?) {
            with(newInstance(context)) {
                this.delete(scaffale)
            }

        }

        fun lista(context: Context?): List<Cassetto> {
            with(newInstance(context)) {
                return this.lista()
            }
        }

        fun listaScaffale(context: Context?, scaffale: String): Array<Cassetto?> {
            with(newInstance(context)) {
                return this.listaScaffale(scaffale)
            }
        }

        fun init_table(context: Context?) {
            with(newInstance(context)) {
                this.save(Cassetto("S001", 1, 2))
                this.save(Cassetto("S001", 2, 3))
                this.save(Cassetto("S001", 3, 4))
                this.save(Cassetto("S001", 4, 3))
                this.save(Cassetto("S001", 5, 2))
                this.save(Cassetto("S001", 6, 2))
                this.save(Cassetto("S001", 7, 3))
                this.save(Cassetto("S001", 8, 4))
                this.save(Cassetto("S001", 9, 3))
                this.save(Cassetto("S001", 10, 2))
                this.save(Cassetto("S002", 1, 2))
                this.save(Cassetto("S002", 2, 1))
                this.save(Cassetto("S002", 3, 1))
                this.save(Cassetto("S002", 4, 3))
                this.save(Cassetto("S002", 5, 2))
                this.save(Cassetto("S002", 6, 1))
                this.save(Cassetto("S002", 7, 1))
                this.save(Cassetto("S002", 8, 2))
                this.save(Cassetto("S002", 9, 3))
                this.save(Cassetto("S002", 10, 1))
                this.close()
            }
        }
    }

}