package com.grishma.getarticles.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.grishma.getarticles.model.Articles

/**
 * Converter class
 */
class Converters {
    @TypeConverter
    fun listToJson(value: List<Articles.ArticlesItem.User>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Articles.ArticlesItem.User>::class.java).toList()

    @TypeConverter
    fun listToJson1(value: List<Articles.ArticlesItem.Media>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList1(value: String) = Gson().fromJson(value, Array<Articles.ArticlesItem.Media>::class.java).toList()
}