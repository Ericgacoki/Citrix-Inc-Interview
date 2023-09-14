package com.ericdev.citrixincinterview.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ericdev.citrixincinterview.data.local.dao.LocalUsersDao
import com.ericdev.citrixincinterview.data.local.dao.LoggedInUserDao
import com.ericdev.citrixincinterview.data.local.entity.LoggedInUserEntity
import com.ericdev.citrixincinterview.data.local.entity.UserEntity
import com.ericdev.citrixincinterview.data.local.typeconverter.RoomTypeConverters

@Database(
    entities = [UserEntity::class, LoggedInUserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class CitrixAppDatabase : RoomDatabase() {
    abstract val localUsersDao: LocalUsersDao
    abstract val loggedInUserDao: LoggedInUserDao
}
