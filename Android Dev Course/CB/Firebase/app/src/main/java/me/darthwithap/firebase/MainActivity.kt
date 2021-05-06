package me.darthwithap.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity "
class MainActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 101
    }

    private lateinit var firebaseDatabase: FirebaseDatabase
    private var firebaseUser: FirebaseUser? = null
    lateinit var nameAdapter: NamesAdapter
    private var isSignedIn = false
    var names = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameAdapter = NamesAdapter(names)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            attachListeners()
        } else {
            //logged out
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                        listOf(
                            AuthUI.IdpConfig.EmailBuilder().build(),
                            AuthUI.IdpConfig.PhoneBuilder().build(),
                            AuthUI.IdpConfig.GoogleBuilder().build()
                        )
                    )
                    .build(),
                Companion.RC_SIGN_IN
            )
        }

        rvNames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvNames.adapter = nameAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                //SUCCESSFULLY LOGGED IN
                attachListeners()
                Log.d(TAG, "onActivityResult: ${firebaseUser?.displayName}")
            } else {
                //SIGN IN FAILED
                if (response == null) {
                    Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
                    return
                }
                if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                    return
                }
                Toast.makeText(this, "Error: ${response.error.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun attachListeners() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        val dbRef = firebaseDatabase.reference

        dbRef.child("names").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                previousChildName?.let {
                    dbRef.child("names").child(it)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                            }

                            override fun onDataChange(previousSnapshot: DataSnapshot) {
                                names[names.indexOf(previousSnapshot.value) + 1] =
                                    snapshot.value.toString()
                                nameAdapter.notifyDataSetChanged()
                            }
                        }
                        )
                }
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                names.add(snapshot.value.toString())
                nameAdapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                names.remove(snapshot.value.toString())
                nameAdapter.notifyDataSetChanged()
            }

        })
        btnPublish.setOnClickListener {
            val name = etName.text.toString()
            dbRef.child("names").push().setValue(name)
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}