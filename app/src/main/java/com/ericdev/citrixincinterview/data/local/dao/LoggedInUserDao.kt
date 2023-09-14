package com.ericdev.citrixincinterview.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericdev.citrixincinterview.data.local.entity.LoggedInUserEntity

@Dao
interface LoggedInUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoggedInUser(user: LoggedInUserEntity)

    @Query("SELECT * FROM logged_in_user LIMIT 1")
    suspend fun getLoggedInUser(): LoggedInUserEntity?

    @Query("DELETE FROM logged_in_user")
    suspend fun clearLoggedInUser()
}
