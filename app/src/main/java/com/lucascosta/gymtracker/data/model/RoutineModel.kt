package com.lucascosta.gymtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Routine")
class RoutineModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "routine_id")
    var routineId: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""
}