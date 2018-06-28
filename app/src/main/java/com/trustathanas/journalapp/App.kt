package com.trustathanas.journalapp

import android.app.Application
import android.app.SharedElementCallback
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import com.trustathanas.journalapp.Repositories.JournalRepository
import com.trustathanas.journalapp.Utilities.AppDatabase
import com.trustathanas.journalapp.Utilities.SharedPrefs
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import java.util.concurrent.Executors

class App : Application() {


    companion object {
        lateinit var preferences: SharedPrefs
    }

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPrefs(applicationContext)
        startKoin(this, listOf(getGeneralModule(), getDatabaseModule(), getRepositoryModule(), getViewModelModules(), getLibrariesModule()))
    }

    open fun getGeneralModule() = applicationContext {
        provide(name = "context") { applicationContext }
    }

    /** We are using the Room Object Relational mapper for android
     *  Which takes context as an argument
     *  Note: change the name of the database relevant to your app
     */
    open fun getDatabaseModule(): Module = applicationContext {
        provide(isSingleton = true) { Room.databaseBuilder(get("context"), AppDatabase::class.java, "journal_database_db").build() }
        provide(isSingleton = false) { get<AppDatabase>().journalDao() }
    }

    /** for all you repositories that you need to inject */
    open fun getRepositoryModule(): Module = applicationContext {
        provide { JournalRepository(get(), get()) }
    }

    /** for all your view models that you will need to inject */
    open fun getViewModelModules(): Module = applicationContext {
        viewModel { JournalViewModel(get()) }
    }

    /**
     * get all other libraries that are that need to be injected
     */
    open fun getLibrariesModule(): Module = applicationContext {
        provide(isSingleton = true) { Executors.newCachedThreadPool() }
    }


}