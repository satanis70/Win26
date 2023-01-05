package com.example.win26.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableRoom")
data class BetModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "betPosition")
    val betPosition: Int,
    @ColumnInfo(name = "betName")
    val betName: String,
    @ColumnInfo(name = "betOdd")
    val betOdd: String,
    @ColumnInfo(name = "betAmount")
    val betAmount: String,
    @ColumnInfo(name= "betStatus")
    val betStatus: String,
    @ColumnInfo(name = "bankCapital")
    val bankCapital: String
)