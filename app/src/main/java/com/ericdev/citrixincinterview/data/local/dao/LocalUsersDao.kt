package com.ericdev.citrixincinterview.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericdev.citrixincinterview.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalUsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalUsers(user: List<UserEntity>)

    @Query("SELECT * FROM local_users WHERE user_id = :userId")
    fun getLocalUserById(userId: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM local_users")
    suspend fun getLocalUsers(): List<UserEntity>

    @Query("DELETE FROM local_users")
    suspend fun deleteAllUsers()
}
