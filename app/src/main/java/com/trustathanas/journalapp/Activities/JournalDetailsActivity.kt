package com.trustathanas.journalapp.Activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Utilities.EXTRA_JOURNAL_DETAILS
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import kotlinx.android.synthetic.main.activity_journal_details.*
import org.koin.android.ext.android.inject

class JournalDetailsActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal_details)

        setSupportActionBar(findViewById(R.id.detail_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val passedJournalDetails = intent.getStringExtra(EXTRA_JOURNAL_DETAILS)

        if (passedJournalDetails != null){
            journalViewModel.getJournalList().observe(this, Observer {
                it?.filter {
                    it.id!!.toInt() == passedJournalDetails.toInt()
                }!!.map {
                    println(" \n the record for this is ${it.id}, ${it.title}, ${it.contents}")
                    // display the record values on the ui  on the block
                    tv_journal_details_title.text = it.title
                    tv_journal_details_content.text = it.contents
                }
            })
        }
    }

    fun cancelButtonClicked(view: View){
        onBackPressed()
    }

}
