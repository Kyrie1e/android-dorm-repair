package com.example.repairapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.repairapp.data.RepairOrder
import com.example.repairapp.db.DBHelper
import com.example.repairapp.db.DbContract

class RepairDao(context: Context) {
    private val dbHelper = DBHelper(context)
    private val logDao = RepairLogDao(context)

    fun createOrder(
        studentId: Long,
        studentName: String,
        type: String,
        location: String,
        level: Int,
        description: String
    ): Long {
        val now = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(DbContract.RepairOrderTable.COLUMN_STUDENT_ID, studentId)
            put(DbContract.RepairOrderTable.COLUMN_STUDENT_NAME, studentName)
            put(DbContract.RepairOrderTable.COLUMN_TYPE, type)
            put(DbContract.RepairOrderTable.COLUMN_LOCATION, location)
            put(DbContract.RepairOrderTable.COLUMN_LEVEL, level)
            put(DbContract.RepairOrderTable.COLUMN_DESCRIPTION, description)
            put(DbContract.RepairOrderTable.COLUMN_STATUS, 0)
            putNull(DbContract.RepairOrderTable.COLUMN_HANDLER_ID)
            putNull(DbContract.RepairOrderTable.COLUMN_HANDLER_NAME)
            putNull(DbContract.RepairOrderTable.COLUMN_CLAIM_TIME)
            put(DbContract.RepairOrderTable.COLUMN_CREATE_TIME, now)
            put(DbContract.RepairOrderTable.COLUMN_UPDATE_TIME, now)
        }
        val orderId = dbHelper.writableDatabase.insert(
            DbContract.RepairOrderTable.TABLE_NAME,
            null,
            values
        )
        if (orderId != -1L) {
            logDao.insertLog(orderId, studentId, studentName, "create", null)
        }
        return orderId
    }

    fun queryOrdersForStudent(studentId: Long): List<RepairOrder> {
        val selection = "${DbContract.RepairOrderTable.COLUMN_STUDENT_ID} = ?"
        val selectionArgs = arrayOf(studentId.toString())
        return queryOrders(selection, selectionArgs)
    }

    fun queryOrdersForWorker(): List<RepairOrder> {
        return queryOrders(null, null)
    }

    fun getOrderById(orderId: Long): RepairOrder? {
        val selection = "${DbContract.RepairOrderTable.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(orderId.toString())
        val cursor = dbHelper.readableDatabase.query(
            DbContract.RepairOrderTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        cursor.use {
            return if (it.moveToFirst()) {
                readOrder(it)
            } else {
                null
            }
        }
    }

    fun claimOrder(orderId: Long, workerId: Long, workerName: String): Boolean {
        val now = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(DbContract.RepairOrderTable.COLUMN_HANDLER_ID, workerId)
            put(DbContract.RepairOrderTable.COLUMN_HANDLER_NAME, workerName)
            put(DbContract.RepairOrderTable.COLUMN_STATUS, 1)
            put(DbContract.RepairOrderTable.COLUMN_CLAIM_TIME, now)
            put(DbContract.RepairOrderTable.COLUMN_UPDATE_TIME, now)
        }
        val selection = "${DbContract.RepairOrderTable.COLUMN_ID} = ? AND " +
            "${DbContract.RepairOrderTable.COLUMN_STATUS} = ? AND " +
            "${DbContract.RepairOrderTable.COLUMN_HANDLER_ID} IS NULL"
        val selectionArgs = arrayOf(orderId.toString(), "0")
        val rowsAffected = dbHelper.writableDatabase.update(
            DbContract.RepairOrderTable.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
        if (rowsAffected == 1) {
            logDao.insertLog(orderId, workerId, workerName, "claim", null)
        }
        return rowsAffected == 1
    }

    fun markDone(orderId: Long, workerId: Long): Boolean {
        val now = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(DbContract.RepairOrderTable.COLUMN_STATUS, 2)
            put(DbContract.RepairOrderTable.COLUMN_UPDATE_TIME, now)
        }
        val selection = "${DbContract.RepairOrderTable.COLUMN_ID} = ? AND " +
            "${DbContract.RepairOrderTable.COLUMN_HANDLER_ID} = ?"
        val selectionArgs = arrayOf(orderId.toString(), workerId.toString())
        val rowsAffected = dbHelper.writableDatabase.update(
            DbContract.RepairOrderTable.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
        if (rowsAffected == 1) {
            val workerName = getUserNameById(workerId)
            logDao.insertLog(orderId, workerId, workerName, "update_status", "DONE")
        }
        return rowsAffected == 1
    }

    private fun queryOrders(selection: String?, selectionArgs: Array<String>?): List<RepairOrder> {
        val cursor = dbHelper.readableDatabase.query(
            DbContract.RepairOrderTable.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            "${DbContract.RepairOrderTable.COLUMN_CREATE_TIME} DESC"
        )
        val orders = mutableListOf<RepairOrder>()
        cursor.use {
            while (it.moveToNext()) {
                orders.add(readOrder(it))
            }
        }
        return orders
    }

    private fun readOrder(cursor: Cursor): RepairOrder {
        val handlerId = if (cursor.isNull(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_HANDLER_ID))) {
            null
        } else {
            cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_HANDLER_ID))
        }
        val handlerName = if (cursor.isNull(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_HANDLER_NAME))) {
            null
        } else {
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_HANDLER_NAME))
        }
        val claimTime = if (cursor.isNull(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_CLAIM_TIME))) {
            null
        } else {
            cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_CLAIM_TIME))
        }
        return RepairOrder(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_ID)),
            studentId = cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_STUDENT_ID)),
            studentName = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_STUDENT_NAME)),
            type = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_TYPE)),
            location = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_LOCATION)),
            level = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_LEVEL)),
            description = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_DESCRIPTION)),
            status = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_STATUS)),
            handlerId = handlerId,
            handlerName = handlerName,
            claimTime = claimTime,
            createTime = cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_CREATE_TIME)),
            updateTime = cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.RepairOrderTable.COLUMN_UPDATE_TIME))
        )
    }

    private fun getUserNameById(userId: Long): String {
        val selection = "${DbContract.UserTable.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(userId.toString())
        val cursor = dbHelper.readableDatabase.query(
            DbContract.UserTable.TABLE_NAME,
            arrayOf(DbContract.UserTable.COLUMN_USERNAME),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        cursor.use {
            return if (it.moveToFirst()) {
                it.getString(it.getColumnIndexOrThrow(DbContract.UserTable.COLUMN_USERNAME))
            } else {
                ""
            }
        }
    }
}
