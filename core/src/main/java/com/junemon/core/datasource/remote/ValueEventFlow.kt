package com.junemon.core.datasource.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


sealed class PushFirebase {
    data class Changed(val snapshot: DataSnapshot) : PushFirebase()
    data class Cancelled(val error: DatabaseError) : PushFirebase()
}

fun DatabaseReference.valueEventFlow(): Flow<PushFirebase> = callbackFlow {

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot): Unit =
            trySendBlocking(PushFirebase.Changed(snapshot)).getOrThrow()

        override fun onCancelled(error: DatabaseError): Unit =
            trySendBlocking(PushFirebase.Cancelled(error)).getOrThrow()
    }
    addValueEventListener(valueEventListener)
    awaitClose {
        removeEventListener(valueEventListener)
    }
}