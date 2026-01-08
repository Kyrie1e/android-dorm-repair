package com.example.repairapp.dao

import android.content.ContentValues
import android.content.Context
import com.example.repairapp.db.DBHelper
import com.example.repairapp.db.DbContract

class RepairLogDao(context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertLog(
        orderId: Long,
        operatorId: Long,
        operatorName: String,
        action: String,
        note: String?
    ): Long {
        val values = ContentValues().apply {
            put(DbContract.RepairLogTable.COLUMN_ORDER_ID, orderId)
            put(DbContract.RepairLogTable.COLUMN_OPERATOR_ID, operatorId)
            put(DbContract.RepairLogTable.COLUMN_OPERATOR_NAME, operatorName)
            put(DbContract.RepairLogTable.COLUMN_ACTION, action)
            put(DbContract.RepairLogTable.COLUMN_NOTE, note)
            put(DbContract.RepairLogTable.COLUMN_TIME, System.currentTimeMillis())
        }
        return dbHelper.writableDatabase.insert(
            DbContract.RepairLogTable.TABLE_NAME,
            null,
            values
        )
    }
}
