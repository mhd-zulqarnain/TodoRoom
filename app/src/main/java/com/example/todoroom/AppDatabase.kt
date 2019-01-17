package com.example.todoroom

import android.arch.persistence.room.*
import android.content.Context

@Database(entities = [TaskEntry::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {

        var sInstance: AppDatabase? = null
        val DATABASE_NAME = "todolist"
        val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        AppDatabase.DATABASE_NAME
                    )
                        .build()
                }
            }
            return sInstance!!
        }
    }
    abstract fun taskDao():TaskDao

}
