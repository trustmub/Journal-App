package com.trustathanas.journalapp.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Rooms.JournalEntity
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_save.setOnClickListener {
            getNewJournal()
            et_journal_title.text.clear()
            et_journal_content.text.clear()
            Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
        }

    }

    private fun getNewJournal() {
        val title = et_journal_title.text.toString()
        val content = et_journal_content.text.toString()
        val journalEntry = JournalEntity(title = title, contents = content)

        journalViewModel.insertJournalEntry(journalEntry)

    }


}
