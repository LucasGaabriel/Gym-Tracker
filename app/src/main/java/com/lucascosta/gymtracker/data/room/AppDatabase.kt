package com.lucascosta.gymtracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.data.dao.ExerciseDAO
import com.lucascosta.gymtracker.data.dao.RoutineDAO
import com.lucascosta.gymtracker.data.model.Converters
import com.lucascosta.gymtracker.data.model.RoutineExerciseCrossRef

@Database(
    entities = [ExerciseModel::class, RoutineModel::class, RoutineExerciseCrossRef::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun ExerciseDAO(): ExerciseDAO
    abstract fun RoutineDAO(): RoutineDAO

    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getDatabase(context: Context): AppDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(AppDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "gymtracker_db.db")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}