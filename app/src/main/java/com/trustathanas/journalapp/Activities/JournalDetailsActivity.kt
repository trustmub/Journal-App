package com.trustathanas.journalapp.Activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Utilities.EXTRA_JOURNAL
import com.trustathanas.journalapp.Utilities.EXTRA_JOURNAL_DETAILS
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import com.trustathanas.journalapp.models.Journal
import kotlinx.android.synthetic.main.activity_journal_details.*
import org.koin.android.ext.android.inject

class JournalDetailsActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()
    private lateinit var journalParcelable: Parcelable

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
                    // display the record values on the ui  on the block
                    tv_journal_details_title.text = it.title
                    tv_journal_details_content.text = it.contents

                    journalParcelable = Journal(it.id!!.toInt(), it.title, it.contents)
                }
            })
        }
    }

    fun cancelButtonClicked(view: View){
        onBackPressed()
    }

    fun editButtonPressed(view: View){
        val intent = Intent(this, EditJournalActivity::class.java)
        intent.putExtra(EXTRA_JOURNAL, journalParcelable)
        startActivity(intent)
    }

}
