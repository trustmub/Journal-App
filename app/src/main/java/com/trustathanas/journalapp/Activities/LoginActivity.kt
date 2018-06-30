package com.trustathanas.journalapp.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.trustathanas.journalapp.App
import com.trustathanas.journalapp.R
import com.trustathanas.journalapp.Utilities.GOOGLE_DISPLAY
import com.trustathanas.journalapp.Utilities.RC_SIGN_IN
import com.trustathanas.journalapp.models.LoginCredentialParcelable
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        if (App.preferences.isLoggedIn){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN && !App.preferences.isLoggedIn) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            App.preferences.let {
                it.isLoggedIn = true
                it.displayName = account.displayName.toString()
                it.familyName = account.familyName.toString()
                it.userEmail = account.email.toString()
            }
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            updateUI(null)
        }

    }

    private fun updateUI(account: GoogleSignInAccount?) {

        if (account != null) {
            account.let {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(GOOGLE_DISPLAY, LoginCredentialParcelable(it.displayName.toString(), it.email.toString(), it.familyName.toString()))
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
        }

    }
}

