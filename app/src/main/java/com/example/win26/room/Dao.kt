package com.example.win26.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.win26.model.BetModel

@androidx.room.Dao
interface Dao {
    @Query("SELECT * FROM tableRoom")
    fun getAllBet():List<BetModel>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBet(databaseModel: BetModel)
    @Delete()
    suspend fun deleteBet(databaseModel: BetModel)
    @Query("DELETE FROM tableRoom")
    suspend fun deleteDatabase()
    @Query("UPDATE tableRoom SET betStatus=:status WHERE betPosition LIKE:position")
    suspend fun updateBet(status: String, position: Int)
    @Query("UPDATE tableRoom SET bankCapital=:capital WHERE betPosition LIKE:position")
    suspend fun updateCapital(capital:String, position: Int)
}