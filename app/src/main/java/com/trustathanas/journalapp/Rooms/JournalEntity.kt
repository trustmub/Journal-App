package com.trustathanas.journalapp.Rooms

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long? = null,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "contents") val contents: String

)
