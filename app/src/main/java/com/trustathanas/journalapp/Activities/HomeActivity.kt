package com.trustathanas.journalapp.Activities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.trustathanas.journalapp.Adapters.JournalListAdapter
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()
    private lateinit var adapter: JournalListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        layoutManager = LinearLayoutManager(this)

        fab_add.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        getJournalFromDatabase()


    }

    override fun onResume() {
        super.onResume()
        getJournalFromDatabase()
    }

    private fun getJournalFromDatabase() {

        journalViewModel.getJournalList()
                .observe(this, Observer {
                    adapter = JournalListAdapter(this, it!!) // will implement the clickable event using lambda {}

                    print("the number of records is ${it.count()}")
                    Log.d("Home", "Count is =: ${it.count()}")

                    rv_journal_list.let {
                        it.adapter = adapter
                        it.layoutManager = layoutManager
                        it.setHasFixedSize(true)
                    }
                })

    }
}
