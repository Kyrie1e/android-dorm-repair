package com.example.repairapp.data

data class RepairOrder(
    val id: Long,
    val studentId: Long,
    val studentName: String,
    val type: String,
    val location: String,
    val level: Int,
    val description: String,
    val status: Int,
    val handlerId: Long?,
    val handlerName: String?,
    val claimTime: Long?,
    val createTime: Long,
    val updateTime: Long
)
