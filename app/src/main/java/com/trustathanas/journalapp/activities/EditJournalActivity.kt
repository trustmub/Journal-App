package com.trustathanas.journalapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Rooms.JournalEntity
import com.trustathanas.journalapp.Utilities.EXTRA_JOURNAL
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import com.trustathanas.journalapp.models.Journal
import kotlinx.android.synthetic.main.activity_edit_journal.*
import org.koin.android.ext.android.inject

class EditJournalActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()
    private lateinit var updateRecord: JournalEntity
    private var recordId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_journal)

        val recordDetails = intent.getParcelableExtra<Journal>(EXTRA_JOURNAL)

        et_edit_journal_title.setText(recordDetails.title)
        et_edit_journal_contents.setText(recordDetails.contents)

        recordId = recordDetails.record_id


    }

    fun editSaveButtonClicked(view: View) {
        updateRecord = JournalEntity(recordId, et_edit_journal_title.text.toString(), et_edit_journal_contents.text.toString())

        journalViewModel.updateJournal(updateRecord)
        Toast.makeText(this, "Record Added to Database", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomeActivity::class.java))
    }

    fun editCancelButtonClicked(view: View) {
        onBackPressed()
    }
}
