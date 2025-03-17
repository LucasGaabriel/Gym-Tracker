package com.lucascosta.gymtracker.utils

/**
 * A classe [Constants] contém constantes utilizadas em todo o aplicativo para garantir que valores
 * específicos sejam reutilizados de forma consistente.
 *
 * As constantes estão organizadas em objetos, como o [DB_MSGS], que armazena códigos de mensagens
 * relacionados ao banco de dados, com valores para diferentes estados de operação (sucesso, falha, etc.).
 */
class Constants {
    object DB_MSGS {
        const val FAIL = 0
        const val SUCCESS = 1
        const val CONSTRAINT = 2
        const val NOT_FOUND = 3
        const val EXERCISE_ALREADY_EXISTS = 4
    }
}