package com.trustathanas.journalapp.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Rooms.JournalEntity
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class AddJournalActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun saveJournalButtonClicked(view: View) {
        val title = et_journal_title.text.toString()
        val content = et_journal_content.text.toString()
        if (title != "" || content != "") {
            val journalEntry = JournalEntity(title = title, contents = content)
            journalViewModel.insertJournalEntry(journalEntry)
            journalViewModel.getJournalList()
            startActivity(Intent(this, HomeActivity::class.java))

        } else {
            Toast.makeText(this, "Cannot save empty Journal Item", Toast.LENGTH_SHORT).show()
        }

    }

    fun addNewJournalCancelClicked(view: View){
        onBackPressed()
    }


}
