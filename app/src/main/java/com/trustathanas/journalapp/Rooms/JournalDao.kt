package com.trustathanas.journalapp.Rooms

import android.arch.persistence.room.*

/**
 *  An interface for accessing the SQLite database using Room libraries @Dao annotation
 *
 */



@Dao
interface JournalDao {

    /**
     * method for inserting new record in the database
     *
     * @param journal   JournalEntity object
     * @return Unit
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewJournal(journal: JournalEntity) {
    }

    /**
     * method for updating specific record in the database
     *
     * @param journal   JournalEntity
     * @return Unit
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateJournal(journal: JournalEntity)

    /**
     * method for retrieving all journal records in the database
     *
     * @return List<JournalEntity>
     */
    @Query("SELECT * FROM journal_entries ")
    fun getJournals(): List<JournalEntity>

    /**
     * method for deleting a specific record in the database
     *
     * @param journal   JournalEntity
     * @return Unit
     */
    @Delete
    fun deleteJournal(journal: JournalEntity)
}