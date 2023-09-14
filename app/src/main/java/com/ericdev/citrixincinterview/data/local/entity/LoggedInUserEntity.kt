package com.ericdev.citrixincinterview.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logged_in_user")
data class LoggedInUserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String
)
