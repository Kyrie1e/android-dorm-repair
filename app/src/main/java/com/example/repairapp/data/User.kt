package com.example.repairapp.data

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val role: String,
    val createTime: Long
)
