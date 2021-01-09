package com.hilt.newszilla.data.local.mappers

import androidx.room.TypeConverter
import com.hilt.newszilla.data.remote.response.headlineResponse.Source


class MyConverters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }
    @TypeConverter
    fun toSource(name: String):Source {
        return Source(name,name)
    }


}