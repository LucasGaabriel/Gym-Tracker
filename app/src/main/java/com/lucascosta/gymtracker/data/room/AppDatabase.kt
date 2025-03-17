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

/**
 * Banco de dados do aplicativo [GymTracker], usando o Room.
 *
 * Essa classe define o banco de dados que contém as entidades [ExerciseModel], [RoutineModel] e [RoutineExerciseCrossRef].
 * Também configura os DAOs (Data Access Objects) responsáveis pelo acesso a essas entidades no banco de dados.
 */
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