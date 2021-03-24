package com.junemon.core.remote.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun DatabaseReference.singleValueEvent(): PushFirebase =
    suspendCancellableCoroutine { continuation ->

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                continuation.resume(PushFirebase.Cancelled(error))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(PushFirebase.Changed(snapshot))
            }
        }
        addListenerForSingleValueEvent(valueEventListener) // Subscribe to the event
    }


 fun DatabaseReference.valueEventFlow(): Flow<PushFirebase> = callbackFlow {
    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot): Unit =
            sendBlocking(PushFirebase.Changed(snapshot))

        override fun onCancelled(error: DatabaseError): Unit =
            sendBlocking(PushFirebase.Cancelled(error))
    }
    addValueEventListener(valueEventListener)
    awaitClose {
        removeEventListener(valueEventListener)
    }
}

fun FirebaseAuth.valueEventProfileFlow(): Flow<FirebaseAuth> = channelFlow {
    val profileListener = FirebaseAuth.AuthStateListener {
        channel.offer(it)
    }
    addAuthStateListener(profileListener)
    awaitClose {
        removeAuthStateListener(profileListener)
    }
}