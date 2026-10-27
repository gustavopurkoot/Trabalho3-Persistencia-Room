package com.example.projeto_desenvolvimento_mobile

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = [Unidade::class, Consulta::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun unidadeDAO(): UnidadeDAO
    abstract fun consultaDAO(): ConsultaDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}