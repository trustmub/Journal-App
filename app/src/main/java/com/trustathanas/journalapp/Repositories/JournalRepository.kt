package com.trustathanas.journalapp.Repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.trustathanas.journalapp.App
import com.trustathanas.journalapp.Rooms.JournalDao
import com.trustathanas.journalapp.Rooms.JournalEntity
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

class JournalRepository(private val journalDao: JournalDao, private val executorService: ExecutorService) {

    fun getAllJournals(): LiveData<List<JournalEntity>> {
        val journalEntries: MutableLiveData<List<JournalEntity>> = MutableLiveData()
        executorService.execute {
            journalEntries.postValue(journalDao.getJournals())
        }
        return journalEntries
    }

    fun insertJournalEntry(journal: JournalEntity) {
        executorService.execute {
            journalDao.insertNewJournal(journal)
        }
    }

}

