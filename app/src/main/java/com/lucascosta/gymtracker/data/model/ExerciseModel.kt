package com.lucascosta.gymtracker.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

import java.io.Serializable

@Parcelize
@Entity(tableName = "Exercise")
class ExerciseModel : Serializable, Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exercise_id")
    var exerciseId: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "primary_muscle")
    var primaryMuscle: String = ""

    @ColumnInfo(name = "sets")
    var sets: Int = 0

    @ColumnInfo(name = "reps")
    var reps: Int = 0

    @ColumnInfo(name = "weight")
    var weight: Float = 0f

    @ColumnInfo(name = "notes")
    var notes: String = ""

    @ColumnInfo(name = "personal_record")
    var personalRecord: Float = 0f
}