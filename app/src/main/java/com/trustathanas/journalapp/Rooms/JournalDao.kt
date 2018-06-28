package com.trustathanas.journalapp.Rooms

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface JournalDao {

    /**This will create a new Journal Entry.
     *
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewJournal(journal: JournalEntity) {
    }

    /**This will get all the Journals in the database.
     *
     * */
    @Query("SELECT * FROM journal_entries ")
    fun getJournals(): List<JournalEntity>


    /** This will delete that particular Journal record passed on the
     * function as an argument
     */
    @Delete
    fun deleteJournal(user: JournalEntity)
}