package com.erplsf.scrubber

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HashMapConverter {
    @TypeConverter
    fun fromHashMap(value: HashMap<String, String>?): String? {
        return value?.let {
            Gson().toJson(it)
        }
    }

    @TypeConverter
    fun toHashMap(value: String?): HashMap<String, String>? {
        val type = object : TypeToken<HashMap<String, String>>() {}.type
        return value?.let {
            Gson().fromJson(it, type)
        }
    }
}