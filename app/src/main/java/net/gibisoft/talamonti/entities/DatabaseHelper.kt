package net.gibisoft.talamonti.entities

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.concurrent.Volatile


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE1)
        db.execSQL(CREATE_TABLE2)
        db.execSQL(CREATE_TABLE3)
        db.execSQL(CREATE_VIEW1)/**/
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS scaffali;")
        db.execSQL("DROP TABLE IF EXISTS cassetti;")
        db.execSQL("DROP TABLE IF EXISTS utensili;")
        db.execSQL("DROP VIEW IF EXISTS cassetti_view;")
        onCreate(db)
    }

    companion object {
        // Database Information
        const val DB_NAME = "talamonti.db3"

        // database version
        const val DB_VERSION = 1

        // Creating table query
        private const val CREATE_TABLE1 = ("CREATE TABLE scaffali (" +
                "codice	VARCHAR(10) NOT NULL," +
                "indirizzo	VARCHAR(20) NOT NULL," +
                "porta	INT NOT NULL," +
                "PRIMARY KEY(codice));")
        private const val CREATE_TABLE2 = ("CREATE TABLE cassetti ( " +
                "scaffale	TEXT NOT NULL," +
                "posizione	INTEGER NOT NULL," +
                "capacita	INTEGER NOT NULL CHECK(capacita > 0 AND capacita < 6)," +
                "PRIMARY KEY(scaffale, posizione));")

        private const val CREATE_TABLE3 = ("CREATE TABLE utensili (" +
                "codice	VARCHAR(20) NOT NULL," +
                "descrizione	VARCHAR(200)," +
                "scaffale	VARCHAR(10) NOT NULL DEFAULT 'S001'," +
                "posizione	INT NOT NULL DEFAULT 0," +
                "PRIMARY KEY(codice)," +
                "FOREIGN KEY(scaffale) REFERENCES scaffali(codice));")

        private const val CREATE_VIEW1 = ("CREATE VIEW cassetti_view AS " +
                "SELECT cassetti.scaffale, cassetti.posizione, cassetti.capacita, " +
                "coalesce(COUNT(utensili.codice),0) as numpezzi " +
                "FROM cassetti LEFT JOIN utensili ON cassetti.scaffale=utensili.scaffale " +
                "AND cassetti.posizione=utensili.posizione " +
                "GROUP BY cassetti.scaffale, cassetti.posizione")
    }
}