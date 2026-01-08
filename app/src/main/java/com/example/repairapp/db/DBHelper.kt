package com.example.repairapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DbContract.DATABASE_NAME,
    null,
    DbContract.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE ${DbContract.UserTable.TABLE_NAME} (
              ${DbContract.UserTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
              ${DbContract.UserTable.COLUMN_USERNAME} TEXT NOT NULL UNIQUE,
              ${DbContract.UserTable.COLUMN_PASSWORD} TEXT NOT NULL,
              ${DbContract.UserTable.COLUMN_ROLE} TEXT NOT NULL CHECK(${DbContract.UserTable.COLUMN_ROLE} IN ('student','worker')),
              ${DbContract.UserTable.COLUMN_CREATE_TIME} INTEGER NOT NULL
            );
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE ${DbContract.RepairOrderTable.TABLE_NAME} (
              ${DbContract.RepairOrderTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
              ${DbContract.RepairOrderTable.COLUMN_STUDENT_ID} INTEGER NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_STUDENT_NAME} TEXT NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_TYPE} TEXT NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_LOCATION} TEXT NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_LEVEL} INTEGER NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_DESCRIPTION} TEXT NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_STATUS} INTEGER NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_HANDLER_ID} INTEGER,
              ${DbContract.RepairOrderTable.COLUMN_HANDLER_NAME} TEXT,
              ${DbContract.RepairOrderTable.COLUMN_CLAIM_TIME} INTEGER,
              ${DbContract.RepairOrderTable.COLUMN_CREATE_TIME} INTEGER NOT NULL,
              ${DbContract.RepairOrderTable.COLUMN_UPDATE_TIME} INTEGER NOT NULL
            );
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE ${DbContract.RepairLogTable.TABLE_NAME} (
              ${DbContract.RepairLogTable.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
              ${DbContract.RepairLogTable.COLUMN_ORDER_ID} INTEGER NOT NULL,
              ${DbContract.RepairLogTable.COLUMN_OPERATOR_ID} INTEGER NOT NULL,
              ${DbContract.RepairLogTable.COLUMN_OPERATOR_NAME} TEXT NOT NULL,
              ${DbContract.RepairLogTable.COLUMN_ACTION} TEXT NOT NULL,
              ${DbContract.RepairLogTable.COLUMN_NOTE} TEXT,
              ${DbContract.RepairLogTable.COLUMN_TIME} INTEGER NOT NULL
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DbContract.RepairLogTable.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DbContract.RepairOrderTable.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DbContract.UserTable.TABLE_NAME}")
        onCreate(db)
    }
}
