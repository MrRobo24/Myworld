package com.example.myworld.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AuthEntity::class], version = 1)
abstract class MyWorldDB : RoomDatabase() {
    abstract fun authDao(): AuthDao
}