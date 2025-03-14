package com.lucascosta.gymtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Routine")
class RoutineModel : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "routine_id")
    var routineId: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""
}