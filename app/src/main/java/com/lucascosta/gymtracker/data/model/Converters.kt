package com.lucascosta.gymtracker.data.model

import androidx.room.TypeConverter

/**
 * Classe de conversores de tipo para a aplicação, utilizada pelo Room para converter tipos personalizados
 * durante o armazenamento e recuperação de dados no banco de dados.
 */
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
