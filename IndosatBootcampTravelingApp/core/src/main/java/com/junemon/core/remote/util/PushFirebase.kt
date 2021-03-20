package com.junemon.core.remote.util

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

sealed class PushFirebase {
    data class Changed(val snapshot: DataSnapshot): PushFirebase()
    data class Cancelled(val error: DatabaseError): PushFirebase()
}