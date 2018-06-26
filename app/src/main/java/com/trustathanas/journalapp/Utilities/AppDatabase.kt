package com.trustathanas.journalapp.Utilities

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.trustathanas.journalapp.Rooms.JournalDao
import com.trustathanas.journalapp.Rooms.JournalEntity


@Database(entities = arrayOf(JournalEntity::class), version = 1, exportSchema = true)
open abstract class AppDatabase: RoomDatabase() {
    /** Dao functions */
    abstract fun journalDao(): JournalDao
}