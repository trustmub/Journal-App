package com.trustathanas.journalapp.activities

import android.arch.lifecycle.Observer
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.trustathanas.journalapp.Adapters.JournalListAdapter
import com.trustathanas.journalapp.App
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Utilities.EXTRA_JOURNAL_DETAILS
import com.trustathanas.journalapp.ViewModel.JournalViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt

class HomeActivity : AppCompatActivity() {

    private val journalViewModel by inject<JournalViewModel>()
    private lateinit var adapter: JournalListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(findViewById(R.id.home_toolbar))

        //Tutorial for the UI elements on the HomeActivity
        showFabPrompt()


        tv_display_name.text = App.preferences.userEmail
        home_toolbar.title = "${App.preferences.displayName}'s Journal"

        layoutManager = LinearLayoutManager(this)

        fab_add.setOnClickListener {
            val intent = Intent(this, AddJournalActivity::class.java)
            startActivity(intent)
        }
        getJournalFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_settings_item -> {
                DialogUtil.createTermsOfServiceDialog(this,
                        "Terms and Conditions",
                        "this is the terms and conditions message").show()
                true
            }
            R.id.menu_exit_item -> {
                clearAndExitUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearAndExitUser() {
        // clear all the shared preferences on logout
        App.preferences.let {
            it.displayName = ""
            it.familyName = ""
            it.userEmail = ""
            it.isLoggedIn = false

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    /**
     * prevent back button pressed to go back to login activity
     * */
    override fun onBackPressed() {
        counter++
        when (counter) {
            1 -> Toast.makeText(this, "Press back again to close.", Toast.LENGTH_SHORT).show()
            2 -> finishAffinity()
        }

    }

    override fun onResume() {
        super.onResume()
        getJournalFromDatabase()

        counter = 0
    }

    /**
     * this method retrieves the data from the database and present it to the adapter for
     * display on the recycler view.
     */
    private fun getJournalFromDatabase() {
        journalViewModel.getJournalList()
                .observe(this, Observer { it ->
                    adapter = JournalListAdapter(this, it!!) {
                        val detailsIntent = Intent(this, JournalDetailsActivity::class.java)
                        detailsIntent.putExtra(EXTRA_JOURNAL_DETAILS, it.id.toString())
                        startActivity(detailsIntent)
                    }

                    rv_journal_list.let {
                        it.adapter = adapter
                        it.layoutManager = layoutManager
                        it.setHasFixedSize(true)
                        it.isLongClickable = true
                        it.setOnLongClickListener {
                            DialogUtil.createAlertDialogWithOneButton(this,
                                    "Delete Alert",
                                    "Are you sure you want to delete",
                                    "Delete",
                                    DialogInterface.OnClickListener { dialog, _ ->
                                        dialog.dismiss()
                                    }).show()

                            true
                        }
                    }
                })

    }

    /**
     * tutorial on the HomeActivities fab UI which will only show on the first time of user
     * activity on the app
     */
    private fun showFabPrompt() {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        if (!App.preferences.didShowPrompt) {
            MaterialTapTargetPrompt.Builder(this)
                    .setTarget(fab_add)
                    .setPrimaryText(getString(R.string.click_here))
                    .setSecondaryText(getString(R.string.tutorial_empty_list_prompt))
                    .setBackButtonDismissEnabled(true)
                    .setPromptStateChangeListener { _ , state ->
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                            App.preferences.didShowPrompt = true

                        }
                    }
                    .show()

        }
    }
}
