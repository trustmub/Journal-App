package com.trustathanas.journalapp.Activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import com.trustathanas.journalapp.Adapters.JournalListAdapter
import com.trustathanas.journalapp.App
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Utilities.EXTRA_JOURNAL_DETAILS
import com.trustathanas.journalapp.Utilities.GOOGLE_DISPLAY
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import com.trustathanas.journalapp.models.LoginCredentialParcelable
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()
    private lateinit var adapter: JournalListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(findViewById(R.id.home_toolbar))


        val userDetails = intent.getParcelableExtra<LoginCredentialParcelable>(GOOGLE_DISPLAY)

        tv_display_name.text = App.preferences.userEmail
        home_toolbar.title = "${App.preferences.displayName}'s Journal"

        layoutManager = LinearLayoutManager(this)

        fab_add.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        getJournalFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_activity_menu, menu)


        val exitItem = menu?.findItem(R.id.action_exit)
        val exitView = exitItem?.actionView
        exitView?.setOnClickListener {
            // clear all the chared preferences
            App.preferences.let {
                it.displayName = ""
                it.familyName = ""
                it.userEmail = ""
                it.isLoggedIn = false

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * prevent back button pressed to go back to login activity
     * */
    override fun onBackPressed() {
        return
    }

    override fun onResume() {
        super.onResume()
        getJournalFromDatabase()
    }

    private fun getJournalFromDatabase() {
        journalViewModel.getJournalList()
                .observe(this, Observer {
                    adapter = JournalListAdapter(this, it!!) {
                        val detailsIntent = Intent(this, JournalDetailsActivity::class.java)
                        detailsIntent.putExtra(EXTRA_JOURNAL_DETAILS, it.id.toString())
                        startActivity(detailsIntent)
                    }

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
