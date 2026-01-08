package com.example.repairapp.db

object DbContract {
    const val DATABASE_NAME = "repair_app.db"
    const val DATABASE_VERSION = 1

    object UserTable {
        const val TABLE_NAME = "user"
        const val COLUMN_ID = "_id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_ROLE = "role"
        const val COLUMN_CREATE_TIME = "create_time"
    }

    object RepairOrderTable {
        const val TABLE_NAME = "repair_order"
        const val COLUMN_ID = "_id"
        const val COLUMN_STUDENT_ID = "student_id"
        const val COLUMN_STUDENT_NAME = "student_name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_LEVEL = "level"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_STATUS = "status"
        const val COLUMN_HANDLER_ID = "handler_id"
        const val COLUMN_HANDLER_NAME = "handler_name"
        const val COLUMN_CLAIM_TIME = "claim_time"
        const val COLUMN_CREATE_TIME = "create_time"
        const val COLUMN_UPDATE_TIME = "update_time"
    }

    object RepairLogTable {
        const val TABLE_NAME = "repair_log"
        const val COLUMN_ID = "_id"
        const val COLUMN_ORDER_ID = "order_id"
        const val COLUMN_OPERATOR_ID = "operator_id"
        const val COLUMN_OPERATOR_NAME = "operator_name"
        const val COLUMN_ACTION = "action"
        const val COLUMN_NOTE = "note"
        const val COLUMN_TIME = "time"
    }
}
