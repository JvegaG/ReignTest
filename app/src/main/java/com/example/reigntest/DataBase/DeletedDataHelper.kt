package com.example.reigntest.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DeletedDataHelper(
    context: Context
): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object{
        const val DATABASE_NAME = "DELETED_DATA"
        const val DATABASE_VERSION = 1
    }

    object FeedReader{
        object FeedEntry: BaseColumns {
            const val TABLE_NAME = "Data"
            const val COLUMN_NAME_STORY_ID = "storyID"
        }
    }

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedReader.FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedReader.FeedEntry.COLUMN_NAME_STORY_ID} INTEGER)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedReader.FeedEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

}