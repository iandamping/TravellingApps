package com.junemon.core.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.junemon.core.datasource.remote.model.BorneoPlacesData
import com.junemon.core.domain.common.DataHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlacesRemoteDataSourceImpl @Inject constructor(private val databasePlaceReference: DatabaseReference) :
    PlacesRemoteDataSource {
    override fun getFirebaseData(): Flow<DataHelper<List<BorneoPlacesData>>> {
        return databasePlaceReference.valueEventFlow().map { value ->
            when (value) {
                is PushFirebase.Changed -> {
                    val result = value.snapshot.children.mapNotNull {
                        it.getValue(BorneoPlacesData::class.java)
                    }.toList()
                    DataHelper.Success(result)
                }
                is PushFirebase.Cancelled -> {
                    DataHelper.Error(value.error.toException())
                }
            }
        }
    }
}