package com.example.repairapp.dao

import android.content.ContentValues
import android.content.Context
import com.example.repairapp.data.User
import com.example.repairapp.db.DBHelper
import com.example.repairapp.db.DbContract

class UserDao(context: Context) {
    private val dbHelper = DBHelper(context)

    fun registerUser(username: String, password: String, role: String): Long {
        val values = ContentValues().apply {
            put(DbContract.UserTable.COLUMN_USERNAME, username)
            put(DbContract.UserTable.COLUMN_PASSWORD, password)
            put(DbContract.UserTable.COLUMN_ROLE, role)
            put(DbContract.UserTable.COLUMN_CREATE_TIME, System.currentTimeMillis())
        }
        return dbHelper.writableDatabase.insert(
            DbContract.UserTable.TABLE_NAME,
            null,
            values
        )
    }

    fun login(username: String, password: String): User? {
        val selection = "${DbContract.UserTable.COLUMN_USERNAME} = ? AND ${DbContract.UserTable.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = dbHelper.readableDatabase.query(
            DbContract.UserTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        cursor.use {
            if (!it.moveToFirst()) {
                return null
            }
            return User(
                id = it.getLong(it.getColumnIndexOrThrow(DbContract.UserTable.COLUMN_ID)),
                username = it.getString(it.getColumnIndexOrThrow(DbContract.UserTable.COLUMN_USERNAME)),
                password = it.getString(it.getColumnIndexOrThrow(DbContract.UserTable.COLUMN_PASSWORD)),
                role = it.getString(it.getColumnIndexOrThrow(DbContract.UserTable.COLUMN_ROLE)),
                createTime = it.getLong(it.getColumnIndexOrThrow(DbContract.UserTable.COLUMN_CREATE_TIME))
            )
        }
    }
}
