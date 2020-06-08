package com.example.mvvmex.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Akhtar on 08/06/20
 */
@Database(entities = [TodoTable::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun todoDao(): TodoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(Database::class) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase::class.java,
                            "mvvm_db")
                            .build()
                }
            }
            return INSTANCE
        }
    }
}
