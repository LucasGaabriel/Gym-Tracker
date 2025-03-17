package com.lucascosta.gymtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Modelo de dados que representa uma rotina de exercícios na aplicação.
 *
 * Esta classe é uma entidade do Room Database, armazenada na tabela "Routine".
 * A classe implementa [Serializable] para facilitar a transferência de dados entre componentes.
 */
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