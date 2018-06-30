package com.trustathanas.journalapp.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.trustathanas.journalapp.Repositories.JournalRepository
import com.trustathanas.journalapp.Rooms.JournalEntity
import java.util.concurrent.ExecutorService

class JournalViewModel(private val repository: JournalRepository) : ViewModel() {

    fun insertJournalEntry(journal: JournalEntity) = repository.insertJournalEntry(journal)

    fun getJournalList(): LiveData<List<JournalEntity>> = repository.getAllJournals()

    fun updateJournal(journal : JournalEntity) = repository.updateJournal(journal)
}