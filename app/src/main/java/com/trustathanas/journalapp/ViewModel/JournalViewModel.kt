package com.trustathanas.journalapp.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.trustathanas.journalapp.Repositories.JournalRepository
import com.trustathanas.journalapp.Rooms.JournalEntity
import java.util.concurrent.ExecutorService

/**
 * This is a ViewModel class which provides values and data to the View layer of the application
 * this view model is specific to the journals
 *
 * parameters are passed in by Injection
 *
 * @param repository       JournalRepository
 */
class JournalViewModel(private val repository: JournalRepository) : ViewModel() {

    /**
     * Method to provide inserting mechanism journal record for UI
     *
     * @param journal       JournalEntity object
     * @return Unit
     */
    fun insertJournalEntry(journal: JournalEntity) = repository.insertJournalEntry(journal)


    /**
     * Method to provide fetching mechanism journal record list fot UI
     *
     * @return LiveData<List<JournalEntity>>
     */
    fun getJournalList(): LiveData<List<JournalEntity>> = repository.getAllJournals()

    /**
     * Method to provide an update mechanism on a journal record for UI
     *
     * @return Unit
     */
    fun updateJournal(journal: JournalEntity) = repository.updateJournal(journal)
}