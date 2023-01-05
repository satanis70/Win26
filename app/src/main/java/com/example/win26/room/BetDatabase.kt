package com.example.win26.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.win26.model.BetModel

@Database(entities = [BetModel::class], version = 1)
abstract class BetDatabase:androidx.room.RoomDatabase() {
    abstract fun betDao():Dao
    companion object{
        @Volatile
        private var INSTANCE: BetDatabase? = null
        fun getDatabase(context: Context): BetDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BetDatabase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}