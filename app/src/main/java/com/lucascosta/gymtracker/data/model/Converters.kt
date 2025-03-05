package com.lucascosta.gymtracker.data.model

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromPrimaryMuscle(value: PrimaryMuscle?): String? {
        return value?.name
    }

    @TypeConverter
    fun toPrimaryMuscle(value: String?): PrimaryMuscle? {
        return value?.let { PrimaryMuscle.valueOf(it) }
    }
}
