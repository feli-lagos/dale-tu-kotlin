package com.example.myapplication.model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UsuarioEntity::class], version = 1)
abstract class UsuarioDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: UsuarioDatabase? = null

        fun getDatabase(context: Context): UsuarioDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDatabase::class.java,
                    "usuario_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}