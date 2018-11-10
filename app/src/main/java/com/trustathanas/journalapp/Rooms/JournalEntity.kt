package com.trustathanas.journalapp.Rooms

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "journal_entries")
data class JournalEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Int? = null,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "contents") val contents: String,
        @ColumnInfo(name = "date") val date: Date = Date()

)
