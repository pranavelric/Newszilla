package com.hilt.newszilla.utils

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
open class FirebaseUtil @Inject constructor(@ApplicationContext val context: Context) {

    fun getKeyFromDatabase() {

        FirebaseApp.initializeApp(context)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("key")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                AppConstants.API_KEY = dataSnapshot.value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}