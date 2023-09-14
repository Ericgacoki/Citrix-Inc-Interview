package com.ericdev.citrixincinterview.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    val id: String = "",
    val active: Boolean,
    val createdOn: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    @Embedded
    val organization: UserOrganizationEntity,
    val roles: List<UserRoleEntity>,
    val username: String
)
