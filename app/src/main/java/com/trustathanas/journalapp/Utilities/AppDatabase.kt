package com.trustathanas.journalapp.Utilities

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.trustathanas.journalapp.Rooms.Converters
import com.trustathanas.journalapp.Rooms.JournalDao
import com.trustathanas.journalapp.Rooms.JournalEntity


@Database(entities = arrayOf(JournalEntity::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * abstract method for the Data Access Object interface for the journals
     *
     * @return JournalDao
     */
    abstract fun journalDao(): JournalDao
}