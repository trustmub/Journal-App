package com.trustathanas.journalapp.Repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.trustathanas.journalapp.App
import com.trustathanas.journalapp.Rooms.JournalDao
import com.trustathanas.journalapp.Rooms.JournalEntity
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService


/**
 * This is a repository class to manage accessing and manipulation of data in the database
 * for the journal/note entries,
 *
 * parameters are passed in by Injection
 *
 * @param journalDAO       journal Data Access Object
 * @param executorService  ExecutorService for off UI thread execution
 */
class JournalRepository(private val journalDao: JournalDao, private val executorService: ExecutorService) {

    /**
     * Method to get an observable list of all journals in the database
     *
     * @return  LiveData<List<JournalEntity>>
     */
    fun getAllJournals(): LiveData<List<JournalEntity>> {
        val journalEntries: MutableLiveData<List<JournalEntity>> = MutableLiveData()
        executorService.execute {
            journalEntries.postValue(journalDao.getJournals())
        }
        return journalEntries
    }

    /**
     * Method for inserting a new journal record
     *
     * @param journal          JournalEntity instance
     * @return Unit
     */
    fun insertJournalEntry(journal: JournalEntity) {
        executorService.execute {
            journalDao.insertNewJournal(journal)
        }
    }

    /**
     * Method for updating a journal record
     *
     * @param journal          JournalEntity instance
     * @return Unit
     */
    fun updateJournal(journal: JournalEntity) {
        executorService.execute{
            journalDao.updateJournal(journal)
        }

    }

}

