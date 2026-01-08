package com.example.repairapp.data

data class RepairLog(
    val id: Long,
    val orderId: Long,
    val operatorId: Long,
    val operatorName: String,
    val action: String,
    val note: String?,
    val time: Long
)
