package com.trustathanas.journalapp.Activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt

class HomeActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()
    private lateinit var adapter: JournalListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(findViewById(R.id.home_toolbar))

        showFabPrompt()

        val userDetails = intent.getParcelableExtra<LoginCredentialParcelable>(GOOGLE_DISPLAY)

        tv_display_name.text = App.preferences.userEmail
        home_toolbar.title = "${App.preferences.displayName}'s Journal"

        layoutManager = LinearLayoutManager(this)

        fab_add.setOnClickListener {
            val intent = Intent(this, AddJournalActivity::class.java)
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
                    rv_journal_list.let {
                        it.adapter = adapter
                        it.layoutManager = layoutManager
                        it.setHasFixedSize(true)
                    }
                })

    }

    /**
     * tutorial on the
     */
    private fun showFabPrompt() {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefManager.getBoolean("didShowPrompt", false)) {
            MaterialTapTargetPrompt.Builder(this)
                    .setTarget(fab_add)
                    .setPrimaryText(getString(R.string.click_here))
                    .setSecondaryText(getString(R.string.tutorial_empty_list_prompt))
                    .setBackButtonDismissEnabled(true)
                    .setPromptStateChangeListener { prompt, state ->
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                            val prefEditor = prefManager.edit()
                            prefEditor.putBoolean("didShowPrompt", true)
                            prefEditor.apply()
                        }
                    }
                    .show()

        }
    }
}
