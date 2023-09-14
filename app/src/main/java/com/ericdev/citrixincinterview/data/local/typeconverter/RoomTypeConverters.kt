package com.ericdev.citrixincinterview.data.local.typeconverter

import androidx.room.TypeConverter
import com.ericdev.citrixincinterview.data.local.entity.UserRoleEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConverters {
    @TypeConverter
    fun fromUserRoleString(value: String): List<UserRoleEntity> {
        val listType = object : TypeToken<List<UserRoleEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toUserRoleString(value: List<UserRoleEntity>): String {
        return Gson().toJson(value)
    }
}
