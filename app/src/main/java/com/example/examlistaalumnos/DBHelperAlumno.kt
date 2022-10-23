package com.example.examlistaalumnos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object FeedReaderContract {
    // Table contents are grouped together in an anonymous object.
    object FeedEntry : BaseColumns {
        const val nombreTabla = "alumnos"
        const val nombre = "nombre"
        const val cuenta = "cuenta"
        const val correo = "correo"
        const val imagen = "imagen"
    }
}

private const val sqlCreate =
    "CREATE TABLE ${FeedReaderContract.FeedEntry.nombreTabla} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${FeedReaderContract.FeedEntry.nombre} TEXT," +
            "${FeedReaderContract.FeedEntry.cuenta} TEXT," +
            "${FeedReaderContract.FeedEntry.correo} TEXT," +
            "${FeedReaderContract.FeedEntry.imagen} TEXT)"

private const val sqlEliminar = "DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.nombreTabla}"

class DBHelperAlumno (context: Context): SQLiteOpenHelper(context,DB_name,null,DB_version){
    companion object{
        private const val DB_version = 1
        private const val DB_name = "alumnos1.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(sqlEliminar)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}